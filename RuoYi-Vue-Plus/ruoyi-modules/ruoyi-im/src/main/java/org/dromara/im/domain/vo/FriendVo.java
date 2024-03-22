package org.dromara.im.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.im.domain.Friend;
import org.dromara.im.domain.SimpleUser;

import java.io.Serial;
import java.io.Serializable;


/**
 * 好友视图对象 im_friend
 *
 * @author luojiaju
 * @date 2024-01-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Friend.class)
public class FriendVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;

    /**
     * 用户
     */
    @ExcelProperty(value = "用户")
    private Long userId;


    /**
     * 好友
     */
    @ExcelProperty(value = "好友")
    private Long friendId;


    @ExcelProperty(value = "好友信息")
    private SimpleUser friendUser;

    /**
     * 好友关系
     */
    @ExcelProperty(value = "好友关系")
    private Integer relationStatus;

    /**
     * 应用简介
     */
    @ExcelProperty(value = "应用简介")
    private String remark;


}
