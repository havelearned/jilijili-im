package org.dromara.im.domain.bo;

import org.dromara.im.domain.Friend;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 好友业务对象 im_friend
 *
 * @author luojiaju
 * @date 2024-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Friend.class, reverseConvertGenerate = false)
public class FriendBo extends BaseEntity {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户
     */
    @NotNull(message = "用户不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 好友
     */
    @NotNull(message = "好友不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long friendId;

    /**
     * 好友关系
     */
    @NotNull(message = "关系不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer relationStatus;

    /**
     * 应用简介
     */
    private String remark;

    private String userName;


}
