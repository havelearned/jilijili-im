package org.dromara.im.domain.vo;

import org.dromara.im.domain.AppboxClasses;
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
 * 应用分类视图对象 appbox_classes
 *
 * @author luojiaju
 * @date 2024-01-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AppboxClasses.class)
public class AppboxClassesVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 上级分类
     */
    @ExcelProperty(value = "上级分类")
    private Long parentId;

    /**
     * 分类名称
     */
    @ExcelProperty(value = "分类名称")
    private String classesName;

    /**
     * 分类icon
     */
    @ExcelProperty(value = "分类icon")
    private String icon;

    /**
     * 应用简介
     */
    @ExcelProperty(value = "应用简介")
    private String remark;


}
