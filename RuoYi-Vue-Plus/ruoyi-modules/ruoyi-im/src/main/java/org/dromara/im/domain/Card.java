package org.dromara.im.domain;

import org.dromara.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

import java.io.Serial;

/**
 * 社交卡片对象 im_card
 *
 * @author luojiaju
 * @date 2024-02-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("im_card")
public class Card extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 卡片名称
     */
    private String cardName;

    /**
     * 卡片的价格
     */
    private BigDecimal price;

    /**
     * 稀有度
     */
    private String rarity;

    /**
     * 卡片状态
     */
    private Long cardStatus;

    /**
     * 备注
     */
    private String remark;


}
