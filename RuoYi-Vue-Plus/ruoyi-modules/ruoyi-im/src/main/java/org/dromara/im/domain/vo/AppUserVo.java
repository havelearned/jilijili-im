package org.dromara.im.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.im.domain.AppUser;
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
 * 用户应用关联视图对象 appbos_app_user
 *
 * @author Lion Li
 * @date 2024-02-18
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AppUser.class)
public class AppUserVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 应用id
     */
    @ExcelProperty(value = "应用id")
    private Long appId;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 所在应用的状态:1正常 0禁用 2禁言
     */
    @ExcelProperty(value = "所在应用的状态:1正常 0禁用 2禁言", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "im_app_user_status")
    private Long status;

    /**
     * 禁言时间
     */
    @ExcelProperty(value = "禁言时间")
    private Date bannedTime;


}
