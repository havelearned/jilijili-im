package org.dromara.im.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 应用分类对象 appbox_classes
 *
 * @author luojiaju
 * @date 2024-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("appbox_classes")
public class AppboxClasses extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 上级分类
     */
    private Long parentId;

    /**
     * 分类名称
     */
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
