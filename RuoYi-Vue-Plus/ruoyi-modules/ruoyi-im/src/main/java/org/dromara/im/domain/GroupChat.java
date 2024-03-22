package org.dromara.im.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 群聊对象 im_group_chat
 *
 * @author luojiaju
 * @date 2024-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("im_group_chat")
public class GroupChat extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 群主id
     */
    private Long groupLeaderId;

    /**
     * 群聊名称
     */
    private String groupName;

    /**
     * 备注
     */
    private String remark;


}
