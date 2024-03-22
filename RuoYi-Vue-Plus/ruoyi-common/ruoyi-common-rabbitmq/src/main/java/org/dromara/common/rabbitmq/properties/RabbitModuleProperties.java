package org.dromara.common.rabbitmq.properties;

import lombok.Data;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 28/02/2024
 */
@Data
@Configuration
@ConfigurationProperties("spring.rabbitmq")
public class RabbitModuleProperties {

    /**
     * 模块配置
     */
    List<ModuleProperties> modules;

}
