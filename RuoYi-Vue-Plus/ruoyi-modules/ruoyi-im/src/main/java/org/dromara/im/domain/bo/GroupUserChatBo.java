package org.dromara.im.domain.bo;

import org.dromara.im.domain.GroupUserChat;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * groupUserChat业务对象 im_group_user_chat
 *
 * @author luojiaju
 * @date 2024-02-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = GroupUserChat.class, reverseConvertGenerate = false)
public class GroupUserChatBo extends BaseEntity {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 群聊id
     */
    @NotNull(message = "群聊id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long groupId;


}
