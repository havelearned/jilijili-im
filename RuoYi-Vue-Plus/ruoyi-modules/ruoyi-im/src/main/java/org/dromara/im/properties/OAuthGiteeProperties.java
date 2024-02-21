package org.dromara.im.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 16/01/2024
 */
@Component
@Data
@ConfigurationProperties(prefix = "im.gitee-login")
public class OAuthGiteeProperties {
    private Boolean enabled;
    private String clientId;
    private String secrets;
    private String redirectUri;

}
