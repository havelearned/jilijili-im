package org.dromara.im.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 聊天动作对象 im_msg_action
 *
 * @author luojiaju
 * @date 2024-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("im_msg_action")
public class MsgAction extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 聊天id
     */
    private Long msgId;

    /**
     * 赞同量
     */
    private Long endorseCount;

    /**
     * 满分量
     */
    private Long fullCount;

    /**
     * 点赞量
     */
    private Long likeCount;

    /**
     * 备注
     */
    private String remark;


}
