package org.dromara.im.domain.vo;

import org.dromara.im.domain.AppboxFixedAppUserConfig;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 应用的固定用户配置视图对象 appbox_fixed_app_user_config
 *
 * @author luojiaju
 * @date 2024-01-04
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AppboxFixedAppUserConfig.class)
public class AppboxFixedAppUserConfigVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 应用apo
     */
    @ExcelProperty(value = "应用apo")
    private Long appId;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;
    private List<Long> userIds;

    /**
     * 标识
     */
    @ExcelProperty(value = "标识", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "im_appbox_fixed_app_user_config_identity")
    private Long identity;

    /**
     * 头像小图标
     */
    @ExcelProperty(value = "头像小图标")
    private String iconUrl;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;


}
