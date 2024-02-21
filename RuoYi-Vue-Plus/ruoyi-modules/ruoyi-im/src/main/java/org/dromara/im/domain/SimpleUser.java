package org.dromara.im.domain;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@Data
@NoArgsConstructor
@AutoMapper(target = SimpleUser.class)
public class SimpleUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名
     */
    private String deptName;


    /**
     * 用户类型
     */
    private String userType;


    /**
     * 登录时间
     */
    private Long loginTime;


    /**
     * 用户账号
     */
    private String userName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 获取登录id
     */
    public String getLoginId() {
        if (userType == null) {
            throw new IllegalArgumentException("用户类型不能为空");
        }
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        return userType + ":" + userId;
    }

    private Boolean online;

}
