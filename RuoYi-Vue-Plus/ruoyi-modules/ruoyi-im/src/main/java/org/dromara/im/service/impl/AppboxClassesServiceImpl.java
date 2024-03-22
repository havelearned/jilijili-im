package org.dromara.im.service.impl;

import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.im.domain.bo.AppboxClassesBo;
import org.dromara.im.domain.vo.AppboxClassesVo;
import org.dromara.im.domain.AppboxClasses;
import org.dromara.im.mapper.AppboxClassesMapper;
import org.dromara.im.service.IAppboxClassesService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 应用分类Service业务层处理
 *
 * @author luojiaju
 * @date 2024-01-06
 */
@RequiredArgsConstructor
@Service
public class AppboxClassesServiceImpl implements IAppboxClassesService {

    private final AppboxClassesMapper baseMapper;

    /**
     * 查询应用分类
     */
    @Override
    public AppboxClassesVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }


    /**
     * 查询应用分类列表
     */
    @Override
    public List<AppboxClassesVo> queryList(AppboxClassesBo bo) {
        LambdaQueryWrapper<AppboxClasses> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppboxClasses> buildQueryWrapper(AppboxClassesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppboxClasses> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, AppboxClasses::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getClassesName()), AppboxClasses::getClassesName, bo.getClassesName());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), AppboxClasses::getIcon, bo.getIcon());
        return lqw;
    }

    /**
     * 新增应用分类
     */
    @Override
    public Boolean insertByBo(AppboxClassesBo bo) {
        AppboxClasses add = MapstructUtils.convert(bo, AppboxClasses.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改应用分类
     */
    @Override
    public Boolean updateByBo(AppboxClassesBo bo) {
        AppboxClasses update = MapstructUtils.convert(bo, AppboxClasses.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppboxClasses entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除应用分类
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (Boolean.TRUE.equals(isValid)) {
            // 校验分类时候绑定应用
            validIsBindApp(ids);
        }
        boolean b = baseMapper.deleteBatchIds(ids) > 0;
        if(b){
            // 删除关联表
            baseMapper.deleteAssociationClassesByAppId(ids);
        }
        return b;
    }

    private void validIsBindApp(Collection<Long> ids) {
        Long count = baseMapper.selectAssociationClassesCount(ids);
        if (count > 0) {
            throw new ServiceException("删除操作失败,分类绑定有应用无法删除");
        }
    }
}
