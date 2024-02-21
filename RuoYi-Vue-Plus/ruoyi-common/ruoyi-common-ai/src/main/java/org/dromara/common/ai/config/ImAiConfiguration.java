package org.dromara.common.ai.config;

import org.dromara.common.ai.config.properties.AiProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 28/01/2024
 */
@ConditionalOnProperty(prefix = "ai", name = "enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(AiProperties.class)
public class ImAiConfiguration {
}
