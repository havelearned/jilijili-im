package org.dromara.im.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 租户 配置属性
 *
 * @author Lion Li
 */
@Data
@Component
@ConfigurationProperties(prefix = "im")
public class FriendProperties {

    /**
     * 好友数量上线
     */
    private Integer friendCount;

    /**
     * 开启好友校验
     */
    private Boolean enableFriendVerification;

    /**
     * 开启群里校验
     */
    private Boolean enableGroupVerification;



}
