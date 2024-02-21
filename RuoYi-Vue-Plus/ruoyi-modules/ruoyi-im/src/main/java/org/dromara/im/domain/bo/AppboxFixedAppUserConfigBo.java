package org.dromara.im.domain.bo;

import org.dromara.im.domain.AppboxFixedAppUserConfig;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.util.List;

/**
 * 应用的固定用户配置业务对象 appbox_fixed_app_user_config
 *
 * @author luojiaju
 * @date 2024-01-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppboxFixedAppUserConfig.class, reverseConvertGenerate = false)
public class AppboxFixedAppUserConfigBo extends BaseEntity {

    /**
     * id
     */
    private Long id;

    /**
     * 应用apo
     */
    private Long appId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 一个或者多个用户
     */
    private List<Long> userIds;

    /**
     * 标识
     */
    private Long identity;

    /**
     * 头像小图标
     */
    private String iconUrl;

    /**
     * 备注
     */
    private String remark;


}
