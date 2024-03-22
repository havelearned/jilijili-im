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
import org.dromara.im.domain.bo.AppUserBo;
import org.dromara.im.domain.vo.AppUserVo;
import org.dromara.im.domain.AppUser;
import org.dromara.im.mapper.AppUserMapper;
import org.dromara.im.service.IAppUserService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户应用关联Service业务层处理
 *
 * @author Lion Li
 * @date 2024-02-18
 */
@RequiredArgsConstructor
@Service
public class AppUserServiceImpl implements IAppUserService {

    private final AppUserMapper baseMapper;

    /**
     * 查询用户应用关联
     */
    @Override
    public AppUserVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 查询用户应用关联列表
     */
    @Override
    public TableDataInfo<AppUserVo> queryPageList(AppUserBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AppUser> lqw = buildQueryWrapper(bo);
        Page<AppUserVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户应用关联列表
     */
    @Override
    public List<AppUserVo> queryList(AppUserBo bo) {
        LambdaQueryWrapper<AppUser> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppUser> buildQueryWrapper(AppUserBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppUser> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, AppUser::getUserId, bo.getUserId());
        lqw.eq(bo.getAppId() != null, AppUser::getAppId, bo.getAppId());
        lqw.eq(bo.getStatus() != null, AppUser::getStatus, bo.getStatus());
        lqw.between(params.get("beginBannedTime") != null && params.get("endBannedTime") != null,
            AppUser::getBannedTime ,params.get("beginBannedTime"), params.get("endBannedTime"));
        return lqw;
    }

    /**
     * 新增用户应用关联
     */
    @Override
    public Boolean insertByBo(AppUserBo bo) {
        AppUser add = MapstructUtils.convert(bo, AppUser.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改用户应用关联
     */
    @Override
    public Boolean updateByBo(AppUserBo bo) {
        AppUser update = MapstructUtils.convert(bo, AppUser.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppUser entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户应用关联
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
