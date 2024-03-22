package org.dromara.im.config;

import org.dromara.im.properties.OAuthGiteeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 16/01/2024
 */

@ConditionalOnProperty(prefix = "ai.enable", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(OAuthGiteeProperties.class)
public class ImOAuthGiteeConfiguration {
}
