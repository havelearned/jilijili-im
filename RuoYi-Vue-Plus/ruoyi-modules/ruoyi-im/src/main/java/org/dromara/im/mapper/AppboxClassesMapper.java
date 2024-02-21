package org.dromara.im.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.im.domain.AppboxClasses;
import org.dromara.im.domain.vo.AppboxClassesVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 应用分类Mapper接口
 *
 * @author luojiaju
 * @date 2024-01-06
 */
public interface AppboxClassesMapper extends BaseMapperPlus<AppboxClasses, AppboxClassesVo> {

    Long selectAssociationClassesCount(@Param("ids") Collection<Long> ids);


    Long deleteAssociationClassesByAppId(@Param("ids") Collection<Long> ids);

    Long deleteAssociationClassesByClassesId(@Param("ids") Collection<Long> ids);

    Long insertAssociationClassesByClassesIdAndAppId(@Param("classesId")Serializable classesId,
                                                     @Param("appId")Serializable appId);

    Long updateAssociationClassesByClassesIdAndAppId(@Param("classesId")Serializable classesId,
                                                     @Param("appId")Serializable appId);


}
