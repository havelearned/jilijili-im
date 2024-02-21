package org.dromara.im.domain.bo;

import org.dromara.im.domain.MsgAction;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 聊天动作业务对象 im_msg_action
 *
 * @author luojiaju
 * @date 2024-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MsgAction.class, reverseConvertGenerate = false)
public class MsgActionBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 聊天id
     */
    @NotNull(message = "聊天id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long msgId;

    /**
     * 赞同量
     */
    @NotNull(message = "赞同量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long endorseCount;

    /**
     * 满分量
     */
    @NotNull(message = "满分量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long fullCount;

    /**
     * 点赞量
     */
    @NotNull(message = "点赞量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long likeCount;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
