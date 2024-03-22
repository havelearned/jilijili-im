package org.dromara.im.domain.bo;

import org.dromara.im.domain.AppUser;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 用户应用关联业务对象 appbos_app_user
 *
 * @author Lion Li
 * @date 2024-02-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppUser.class, reverseConvertGenerate = false)
public class AppUserBo extends BaseEntity {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 应用id
     */
    @NotNull(message = "应用id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long appId;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;

    /**
     * 所在应用的状态:1正常 0禁用 2禁言
     */
    @NotNull(message = "所在应用的状态:1正常 0禁用 2禁言不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 禁言时间
     */
    @NotNull(message = "禁言时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date bannedTime;


}
