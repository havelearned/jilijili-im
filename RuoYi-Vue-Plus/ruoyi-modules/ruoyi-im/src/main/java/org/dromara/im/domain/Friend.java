package org.dromara.im.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 好友对象 im_friend
 *
 * @author luojiaju
 * @date 2024-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("im_friend")
public class Friend extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 用户
     */
    private Long userId;

    /**
     * 好友
     */
    private Long friendId;

    /**
     * 好友关系
     */
    private Integer relationStatus;

    /**
     * 应用简介
     */
    private String remark;


}
