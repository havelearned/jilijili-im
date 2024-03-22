package org.dromara.im.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.im.domain.ImNotice;

import java.io.Serial;
import java.io.Serializable;


/**
 * 用户通知视图对象 im_notice
 *
 * @author luojiaju
 * @date 2024-01-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = ImNotice.class)
public class ImNoticeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 用户id
     */
    @ExcelProperty(value = "用户id")
    private Long userId;
    @ExcelProperty(value = "昵称")
    private String nickName;

    /**
     * 通知类型:1精选推送 2用户消息 3有人@你
     */
    @ExcelProperty(value = "通知类型:1精选推送 2用户消息 3有人@你")
    private Long noticeType;

    /**
     * 通知标题
     */
    @ExcelProperty(value = "通知标题")
    private String title;

    /**
     * 是否已读:1已读 0未读
     */
    @ExcelProperty(value = "是否已读:1已读 0未读")
    private Integer readStatus;

    /**
     * 通知内容
     */
    @ExcelProperty(value = "通知内容")
    private String content;

    /**
     * 来源url,可前往查看
     */
    @ExcelProperty(value = "来源url,可前往查看")
    private String fromUrl;



}
