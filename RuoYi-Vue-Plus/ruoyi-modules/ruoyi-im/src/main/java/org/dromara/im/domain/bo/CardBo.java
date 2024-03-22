package org.dromara.im.domain.bo;

import org.dromara.im.domain.Card;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * 社交卡片业务对象 im_card
 *
 * @author luojiaju
 * @date 2024-02-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Card.class, reverseConvertGenerate = false)
public class CardBo extends BaseEntity {

    /**
     * id
     */
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 卡片名称
     */
    @NotBlank(message = "卡片名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cardName;

    /**
     * 卡片的价格
     */
    @NotNull(message = "卡片的价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal price;

    /**
     * 稀有度
     */
    @NotBlank(message = "稀有度不能为空", groups = { AddGroup.class, EditGroup.class })
    private String rarity;

    /**
     * 卡片状态
     */
    @NotNull(message = "卡片状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long cardStatus;

    /**
     * 备注
     */
    @NotBlank(message = "备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remark;


}
