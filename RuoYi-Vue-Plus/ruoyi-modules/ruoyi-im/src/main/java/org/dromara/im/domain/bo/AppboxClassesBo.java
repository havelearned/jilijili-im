package org.dromara.im.domain.bo;

import org.dromara.im.domain.AppboxClasses;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 应用分类业务对象 appbox_classes
 *
 * @author luojiaju
 * @date 2024-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppboxClasses.class, reverseConvertGenerate = false)
public class AppboxClassesBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 上级分类
     */
    @NotNull(message = "上级分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String classesName;

    /**
     * 分类icon
     */
    private String icon;

    /**
     * 应用简介
     */
    private String remark;


}
