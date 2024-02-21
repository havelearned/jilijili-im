package org.dromara.im.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.im.domain.bo.GroupUserChatBo;
import org.dromara.im.domain.vo.GroupUserChatVo;
import org.dromara.im.domain.GroupUserChat;
import org.dromara.im.mapper.GroupUserChatMapper;
import org.dromara.im.service.IGroupUserChatService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * groupUserChatService业务层处理
 *
 * @author luojiaju
 * @date 2024-02-10
 */
@RequiredArgsConstructor
@Service
public class GroupUserChatServiceImpl implements IGroupUserChatService {

    private final GroupUserChatMapper baseMapper;

    /**
     * 查询groupUserChat
     */
    @Override
    public GroupUserChatVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 查询groupUserChat列表
     */
    @Override
    public TableDataInfo<GroupUserChatVo> queryPageList(GroupUserChatBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<GroupUserChat> lqw = buildQueryWrapper(bo);
        Page<GroupUserChatVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询groupUserChat列表
     */
    @Override
    public List<GroupUserChatVo> queryList(GroupUserChatBo bo) {
        LambdaQueryWrapper<GroupUserChat> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<GroupUserChat> buildQueryWrapper(GroupUserChatBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<GroupUserChat> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, GroupUserChat::getUserId, bo.getUserId());
        lqw.eq(bo.getGroupId() != null, GroupUserChat::getGroupId, bo.getGroupId());
        return lqw;
    }

    /**
     * 新增groupUserChat
     */
    @Override
    public Boolean insertByBo(GroupUserChatBo bo) {
        GroupUserChat add = MapstructUtils.convert(bo, GroupUserChat.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改groupUserChat
     */
    @Override
    public Boolean updateByBo(GroupUserChatBo bo) {
        GroupUserChat update = MapstructUtils.convert(bo, GroupUserChat.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(GroupUserChat entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除groupUserChat
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
