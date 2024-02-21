package org.dromara.im.domain.vo;

import org.dromara.im.domain.MsgRecord;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.im.domain.SimpleUser;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 聊天消息记录视图对象 im_msg_record
 *
 * @author luojiaju
 * @date 2024-02-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MsgRecord.class)
public class MsgRecordVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id消息
     */
    @ExcelProperty(value = "id消息")
    private Long id;

    /**
     * 发送者
     */
    @ExcelProperty(value = "发送者")
    private Long senderId;

    /**
     * 接收者
     */
    @ExcelProperty(value = "接收者")
    private Long toId;

    /**
     * 群聊
     */
    @ExcelProperty(value = "群聊")
    private Long toGroupId;

    /**
     * 频道
     */
    @ExcelProperty(value = "频道")
    private Long toAppId;

    /**
     * 消息内容
     */
    @ExcelProperty(value = "消息内容")
    private String content;

    /**
     * 内容类型
     */
    @ExcelProperty(value = "内容类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "im_msg_type")
    private Long msgType;

    /**
     * 消息类型
     */
    @ExcelProperty(value = "消息类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "im_msg_to_type")
    private Long toType;

    /**
     * 消息状态
     */
    @ExcelProperty(value = "消息状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "common_status")
    private Long status;

    /**
     * 读取状态
     */
    @ExcelProperty(value = "读取状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "common_status")
    private Long unread;

    /**
     * 引用|回复消息id
     */
    @ExcelProperty(value = "引用|回复消息id")
    private Long refMsgId;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;

    private Date createTime;
    /**
     * 用户发言
     */
    private SimpleUser author;
    /**
     * 消息动作
     */
    private MsgActionVo action;
    private List<MsgRecordVo> chiller = new ArrayList<>();

}
