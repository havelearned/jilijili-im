package org.dromara.im.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 用户通知对象 im_notice
 *
 * @author luojiaju
 * @date 2024-01-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("im_notice")
public class ImNotice extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 通知类型:1精选推送 2用户消息 3有人@你
     */
    private Long noticeType;

    /**
     * 通知标题
     */
    private String title;

    /**
     * 是否已读:1已读 0未读
     */
    private Integer readStatus;

    /**
     * 通知内容
     */
    private String content;

    /**
     * 来源url,可前往查看
     */
    private String fromUrl;


}
