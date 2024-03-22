package org.dromara.im.service.impl;

import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.im.domain.AppboxFixedAppUserConfig;
import org.dromara.im.domain.bo.AppboxFixedAppUserConfigBo;
import org.dromara.im.domain.vo.AppboxFixedAppUserConfigVo;
import org.dromara.im.mapper.AppboxApplicationMapper;
import org.dromara.im.mapper.AppboxFixedAppUserConfigMapper;
import org.dromara.im.service.IAppboxFixedAppUserConfigService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 应用的固定用户配置Service业务层处理
 *
 * @author luojiaju
 * @date 2024-01-04
 */
@RequiredArgsConstructor
@Service
public class AppboxFixedAppUserConfigServiceImpl implements IAppboxFixedAppUserConfigService {

    private final AppboxFixedAppUserConfigMapper baseMapper;

    private final AppboxApplicationMapper appboxApplicationMapper;

    /**
     * 查询应用的固定用户配置
     * @param id appId
     * @return
     */
    @Override
    public AppboxFixedAppUserConfigVo queryById(Long id) {
        // 查询当前应用的配置
        LambdaQueryWrapper<AppboxFixedAppUserConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(true, AppboxFixedAppUserConfig::getAppId, id);
        List<AppboxFixedAppUserConfig> fixedAppUserConfigs = baseMapper.selectList(queryWrapper);
        if (fixedAppUserConfigs == null || fixedAppUserConfigs.isEmpty()) {
            return null;
        } else {
            AppboxFixedAppUserConfig appUserConfig = fixedAppUserConfigs.get(0);
            List<Long> userIds = appboxApplicationMapper.selectVoByAppId(appUserConfig.getAppId());
            AppboxFixedAppUserConfigVo fixedAppUserConfigVo = MapstructUtils.convert(appUserConfig, AppboxFixedAppUserConfigVo.class);
            assert fixedAppUserConfigVo != null;
            fixedAppUserConfigVo.setUserIds(userIds);
            return fixedAppUserConfigVo;
        }
    }

    /**
     * 查询应用的固定用户配置列表
     */
    @Override
    public TableDataInfo<AppboxFixedAppUserConfigVo> queryPageList(AppboxFixedAppUserConfigBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AppboxFixedAppUserConfig> lqw = buildQueryWrapper(bo);
        Page<AppboxFixedAppUserConfigVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询应用的固定用户配置列表
     */
    @Override
    public List<AppboxFixedAppUserConfigVo> queryList(AppboxFixedAppUserConfigBo bo) {
        LambdaQueryWrapper<AppboxFixedAppUserConfig> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppboxFixedAppUserConfig> buildQueryWrapper(AppboxFixedAppUserConfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppboxFixedAppUserConfig> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getId() != null, AppboxFixedAppUserConfig::getId, bo.getId());
        lqw.eq(bo.getAppId() != null, AppboxFixedAppUserConfig::getAppId, bo.getAppId());
        lqw.eq(bo.getUserId() != null, AppboxFixedAppUserConfig::getUserId, bo.getUserId());
        lqw.eq(bo.getIdentity() != null, AppboxFixedAppUserConfig::getIdentity, bo.getIdentity());
        lqw.eq(StringUtils.isNotBlank(bo.getIconUrl()), AppboxFixedAppUserConfig::getIconUrl, bo.getIconUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getRemark()), AppboxFixedAppUserConfig::getRemark, bo.getRemark());
        lqw.between(params.get("beginCreateTime") != null && params.get("endCreateTime") != null,
            AppboxFixedAppUserConfig::getCreateTime, params.get("beginCreateTime"), params.get("endCreateTime"));
        return lqw;
    }

    /**
     * 新增应用的固定用户配置
     */
    @DSTransactional
    @Override
    public Boolean insertByBo(AppboxFixedAppUserConfigBo bo) {
        if (bo.getUserIds() == null || bo.getUserIds().isEmpty()) {
            throw new ServiceException("配置失败,请选择用户");
        } else {
            LambdaQueryWrapper<AppboxFixedAppUserConfig> lw = new LambdaQueryWrapper<>();
            lw.eq(true, AppboxFixedAppUserConfig::getAppId, bo.getAppId());
            baseMapper.delete(lw);
            Long count = 0L;
            List<Long> userIds = bo.getUserIds();
            for (Long userId : userIds) {
                bo.setId(null);
                AppboxFixedAppUserConfig add = MapstructUtils.convert(bo, AppboxFixedAppUserConfig.class);
                assert add != null;
                add.setUserId(userId);
                count += baseMapper.insert(add);
            }
            return count > 0;
        }
    }

    /**
     * 修改应用的固定用户配置
     */
    @Override
    public Boolean updateByBo(AppboxFixedAppUserConfigBo bo) {
        AppboxFixedAppUserConfig update = MapstructUtils.convert(bo, AppboxFixedAppUserConfig.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppboxFixedAppUserConfig entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除应用的固定用户配置
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
