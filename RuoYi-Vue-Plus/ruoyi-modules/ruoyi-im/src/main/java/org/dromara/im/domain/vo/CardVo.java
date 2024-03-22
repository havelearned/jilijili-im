package org.dromara.im.domain.vo;

import java.math.BigDecimal;
import org.dromara.im.domain.Card;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 社交卡片视图对象 im_card
 *
 * @author luojiaju
 * @date 2024-02-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Card.class)
public class CardVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 卡片名称
     */
    @ExcelProperty(value = "卡片名称")
    private String cardName;

    /**
     * 卡片的价格
     */
    @ExcelProperty(value = "卡片的价格")
    private BigDecimal price;

    /**
     * 稀有度
     */
    @ExcelProperty(value = "稀有度", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "im_card_status")
    private String rarity;

    /**
     * 卡片状态
     */
    @ExcelProperty(value = "卡片状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "common_status")
    private Long cardStatus;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
