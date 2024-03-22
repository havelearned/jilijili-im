package org.dromara.im.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.im.domain.MsgAction;
import org.dromara.im.domain.MsgRecord;
import org.dromara.im.domain.SimpleUser;
import org.dromara.im.domain.bo.GroupChatBo;
import org.dromara.im.domain.bo.MsgRecordBo;
import org.dromara.im.domain.vo.MsgRecordVo;
import org.dromara.im.mapper.MsgActionMapper;
import org.dromara.im.mapper.MsgRecordMapper;
import org.dromara.im.service.IMsgRecordService;
import org.dromara.im.utils.ImWebSocketMsgUtils;
import org.dromara.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.dromara.im.utils.SysUserUtils.getCurrentUserIdToLong;

/**
 * 聊天消息记录Service业务层处理
 *
 * @author luojiaju
 * @date 2024-02-20
 */
@RequiredArgsConstructor
@Service
public class MsgRecordServiceImpl implements IMsgRecordService {

    private final MsgRecordMapper baseMapper;
    private final MsgActionMapper msgActionMapper;
    private final SysUserMapper sysUserMapper;
    private final GroupChatServiceImpl groupChatService;

    /**
     * 查询聊天消息记录
     */
    @Override
    public MsgRecordVo queryById(Long id) {
        return baseMapper.selectVoByIdUser(id);

    }

    /**
     * 查询聊天消息记录列表
     */
    @Override
    public TableDataInfo<MsgRecordVo> queryPageList(MsgRecordBo bo, PageQuery pageQuery) {
        Long toType = bo.getToType();
        QueryWrapper<MsgRecord> query = Wrappers.query();
        Page<MsgRecordVo> page = pageQuery.build();
        query.orderByAsc("imr.create_time");
        if (toType.equals(1L)) {
            query.and(i -> i.eq("imr.sender_id", bo.getSenderId()).eq("imr.to_id", bo.getToId())
                    .or()
                    .eq("imr.sender_id", bo.getToId())
                    .eq("imr.to_id", bo.getSenderId()))
                .eq("imr.to_type", 1);
            page = baseMapper.queryUsersOneOnOneChatHistoryWithFriends(query, page);
        } else if (toType.equals(2L)) {
            query.eq("imr.to_group_id", bo.getToGroupId())
                .eq("imr.to_type", 2);
            page = baseMapper.queryChatRecordsOfUserGroupChats(query, page);

        } else {
            query.eq("imr.to_app_id", bo.getToAppId())
                .eq("imr.to_type", 3);
            page = baseMapper.queryChatHistoryOfTheChannel(query, page);
        }
        List<MsgRecordVo> records = page.getRecords();
        // 得当前消息的回复id
        List<MsgRecordVo> newResult = records.stream().map(item -> {
            if (item.getRefMsgId() != null) {
                MsgRecordVo msgRecordVo = baseMapper.selectVoById(item.getId());
                item.getChiller().add(msgRecordVo);
            }
            return item;
        }).toList();

        return TableDataInfo.build(newResult);
    }

    /**
     * 查询聊天消息记录列表
     */
    @Override
    public List<MsgRecordVo> queryList(MsgRecordBo bo) {
        LambdaQueryWrapper<MsgRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MsgRecord> buildQueryWrapper(MsgRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MsgRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getSenderId() != null, MsgRecord::getSenderId, bo.getSenderId());
        lqw.eq(bo.getToId() != null, MsgRecord::getToId, bo.getToId());
        lqw.eq(bo.getToGroupId() != null, MsgRecord::getToGroupId, bo.getToGroupId());
        lqw.eq(bo.getToAppId() != null, MsgRecord::getToAppId, bo.getToAppId());
        lqw.like(StringUtils.isNotBlank(bo.getContent()), MsgRecord::getContent, bo.getContent());
        lqw.eq(bo.getMsgType() != null, MsgRecord::getMsgType, bo.getMsgType());
        lqw.eq(bo.getToType() != null, MsgRecord::getToType, bo.getToType());
        lqw.eq(bo.getStatus() != null, MsgRecord::getStatus, bo.getStatus());
        lqw.eq(bo.getUnread() != null, MsgRecord::getUnread, bo.getUnread());
        lqw.eq(bo.getRefMsgId() != null, MsgRecord::getRefMsgId, bo.getRefMsgId());
        return lqw;
    }

    /**
     * 新增聊天消息记录
     */
    @Override
    public MsgRecordVo insertByBo(MsgRecordBo bo) {
        MsgRecord add = MapstructUtils.convert(bo, MsgRecord.class);
        // 发送者默认时当前请求用户
        add.setSenderId(getCurrentUserIdToLong());
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
            // 添加关联表
            MsgAction action = new MsgAction();
            action.setMsgId(add.getId());
            msgActionMapper.insert(action);

            MsgRecordVo msgRecordVo = baseMapper.selectVoByIdUser(add.getId());
            verifyIfTheOtherPartyIsOnline(msgRecordVo);
            return msgRecordVo;
        } else {
            return null;
        }
    }

    /**
     * 验证对方是否在线
     */
    private void verifyIfTheOtherPartyIsOnline(MsgRecordVo msg) {
        // 单点消息
        if (Objects.nonNull(msg.getToId())) {
            // WebSocket 消息
            ImWebSocketMsgUtils.sendAMessageToTheUser(Long.valueOf(msg.getToId()), msg);
        } else if (!msg.getToGroupId().isBlank() && msg.getToGroupId() != null) {
            // 群发
            String groupId = msg.getToGroupId();
            GroupChatBo bo = new GroupChatBo();
            bo.setId(Long.valueOf(groupId));
            PageQuery query = new PageQuery();
            query.setPageNum(0);
            query.setPageSize(4000); // 群聊配置:群聊最大用户量
            TableDataInfo<SimpleUser> simpleUserTableDataInfo = groupChatService.queryUserListThroughGroupChatId(bo, query);
            List<SimpleUser> rows = simpleUserTableDataInfo.getRows();
            rows.forEach(item -> {
                ImWebSocketMsgUtils.sendAMessageToTheUser(item.getUserId(), msg);
            });
        } else {
            // 发送应用频道消息
        }

    }

    /**
     * 修改聊天消息记录
     */
    @Override
    public Boolean updateByBo(MsgRecordBo bo) {
        MsgRecord update = MapstructUtils.convert(bo, MsgRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MsgRecord entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除聊天消息记录
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean updateByboMsgRead(MsgRecordBo bo) {
        Long userIdToLong = getCurrentUserIdToLong();
        LambdaUpdateWrapper<MsgRecord> luw = Wrappers.lambdaUpdate();
        luw.set(true, MsgRecord::getUnread, 1)
            .eq(Objects.nonNull(bo.getId()), MsgRecord::getId, bo.getId())
            .eq(MsgRecord::getSenderId, bo.getToId())
            .eq(MsgRecord::getToId, userIdToLong)
            .eq(MsgRecord::getToType, 1);

        // 更新
        baseMapper.update(luw);
        return true;
    }


}
