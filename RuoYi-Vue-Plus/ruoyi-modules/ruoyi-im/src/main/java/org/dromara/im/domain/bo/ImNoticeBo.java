package org.dromara.im.domain.bo;
import org.dromara.im.domain.ImNotice;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户通知业务对象 im_notice
 *
 * @author luojiaju
 * @date 2024-01-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ImNotice.class, reverseConvertGenerate = false)
public class ImNoticeBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 通知类型:1精选推送 2用户消息 3有人@你
     */
    @NotNull(message = "通知类型:1精选推送 2用户消息 3有人@你不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long noticeType;

    /**
     * 通知标题
     */
    @NotBlank(message = "通知标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 是否已读:1已读 0未读
     */
    @NotNull(message = "是否已读:1已读 0未读不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer readStatus;

    /**
     * 通知内容
     */
    @NotBlank(message = "通知内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 来源url,可前往查看
     */
    @NotBlank(message = "来源url,可前往查看不能为空", groups = { AddGroup.class, EditGroup.class })
    private String fromUrl;


}
