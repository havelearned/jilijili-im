package org.dromara.im.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.im.domain.ImNotice;
import org.dromara.im.domain.SimpleUser;
import org.dromara.im.domain.bo.ImNoticeBo;
import org.dromara.im.domain.vo.ImNoticeVo;
import org.dromara.im.mapper.ImNoticeMapper;
import org.dromara.im.service.IImNoticeService;
import org.dromara.system.domain.SysUser;
import org.dromara.system.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户通知Service业务层处理
 *
 * @author luojiaju
 * @date 2024-01-05
 */
@RequiredArgsConstructor
@Service
public class ImNoticeServiceImpl implements IImNoticeService {

    private final ImNoticeMapper baseMapper;


    private final SysUserMapper sysUserMapper;

    /**
     * 查询用户通知
     */
    @Override
    public ImNoticeVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询用户通知列表
     */
    @Override
    public TableDataInfo<ImNoticeVo> queryPageList(ImNoticeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ImNotice> lqw = buildQueryWrapper(bo);
        Page<ImNoticeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<ImNoticeVo> records = result.getRecords();
        List<Long> userIds = records.stream().map(ImNoticeVo::getUserId).toList();

        if (!userIds.isEmpty()) {
            List<SysUser> sysUsers = sysUserMapper.selectBatchIds(userIds);
            List<SimpleUser> simpleUsers = MapstructUtils.convert(sysUsers, SimpleUser.class);
            Map<Long, SimpleUser> userMap = simpleUsers.stream()
                .collect(Collectors.toMap(SimpleUser::getUserId, Function.identity()));
            records.forEach(item -> {
                SimpleUser user = userMap.get(item.getUserId());
                if (user != null && item.getUserId().equals(user.getUserId())) {
                    item.setNickName(user.getNickName());
                }
            });
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户通知列表
     */
    @Override
    public List<ImNoticeVo> queryList(ImNoticeBo bo) {
        LambdaQueryWrapper<ImNotice> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ImNotice> buildQueryWrapper(ImNoticeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ImNotice> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, ImNotice::getUserId, bo.getUserId());
        lqw.eq(bo.getNoticeType() != null, ImNotice::getNoticeType, bo.getNoticeType());
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), ImNotice::getTitle, bo.getTitle());
        lqw.eq(bo.getReadStatus() != null, ImNotice::getReadStatus, bo.getReadStatus());
        lqw.like(StringUtils.isNotBlank(bo.getContent()), ImNotice::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getFromUrl()), ImNotice::getFromUrl, bo.getFromUrl());
        return lqw;
    }

    /**
     * 新增用户通知
     */
    @Override
    public Boolean insertByBo(ImNoticeBo bo) {
        ImNotice add = MapstructUtils.convert(bo, ImNotice.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改用户通知
     */
    @Override
    public Boolean updateByBo(ImNoticeBo bo) {
        ImNotice update = MapstructUtils.convert(bo, ImNotice.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ImNotice entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户通知
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
