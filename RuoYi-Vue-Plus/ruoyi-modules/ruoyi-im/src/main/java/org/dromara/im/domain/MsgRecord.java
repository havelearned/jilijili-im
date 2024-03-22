package org.dromara.im.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 聊天消息记录对象 im_msg_record
 *
 * @author luojiaju
 * @date 2024-02-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("im_msg_record")
public class MsgRecord extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id消息
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 发送者
     */
    private Long senderId;

    /**
     * 接收者
     */
    private Long toId;

    /**
     * 群聊
     */
    private Long toGroupId;

    /**
     * 频道
     */
    private Long toAppId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 内容类型
     */
    private Long msgType;

    /**
     * 消息类型
     */
    private Long toType;

    /**
     * 消息状态
     */
    private Long status;

    /**
     * 读取状态
     */
    private Long unread;

    /**
     * 引用|回复消息id
     */
    private Long refMsgId;

    /**
     * 备注
     */
    private String remark;


}
