package org.dromara.im.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * groupUserChat对象 im_group_user_chat
 *
 * @author luojiaju
 * @date 2024-02-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("im_group_user_chat")
public class GroupUserChat extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 群聊id
     */
    private Long groupId;



}
