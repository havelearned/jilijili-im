package org.dromara.im.domain.vo;

import org.dromara.im.domain.MsgAction;
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
 * 聊天动作视图对象 im_msg_action
 *
 * @author luojiaju
 * @date 2024-02-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MsgAction.class)
public class MsgActionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 聊天id
     */
    @ExcelProperty(value = "聊天id")
    private Long msgId;

    /**
     * 赞同量
     */
    @ExcelProperty(value = "赞同量")
    private Long endorseCount;

    /**
     * 满分量
     */
    @ExcelProperty(value = "满分量")
    private Long fullCount;

    /**
     * 点赞量
     */
    @ExcelProperty(value = "点赞量")
    private Long likeCount;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
