package org.dromara.im.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.im.domain.GroupChat;

import java.io.Serial;
import java.io.Serializable;


/**
 * 群聊视图对象 im_group_chat
 *
 * @author luojiaju
 * @date 2024-01-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = GroupChat.class)
public class GroupChatVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelProperty(value = "id")
    private Long id;

    /**
     * 群主id
     */
    @ExcelProperty(value = "群主id")
    private Long groupLeaderId;

    /**
     * 群聊名称
     */
    @ExcelProperty(value = "群聊名称")
    private String groupName;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remark;


}
