package org.dromara.im.service;

import org.dromara.im.domain.AppboxClasses;
import org.dromara.im.domain.vo.AppboxClassesVo;
import org.dromara.im.domain.bo.AppboxClassesBo;

import java.util.Collection;
import java.util.List;

/**
 * 应用分类Service接口
 *
 * @author luojiaju
 * @date 2024-01-06
 */
public interface IAppboxClassesService {

    /**
     * 查询应用分类
     */
    AppboxClassesVo queryById(Long id);


    /**
     * 查询应用分类列表
     */
    List<AppboxClassesVo> queryList(AppboxClassesBo bo);

    /**
     * 新增应用分类
     */
    Boolean insertByBo(AppboxClassesBo bo);

    /**
     * 修改应用分类
     */
    Boolean updateByBo(AppboxClassesBo bo);

    /**
     * 校验并批量删除应用分类信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
