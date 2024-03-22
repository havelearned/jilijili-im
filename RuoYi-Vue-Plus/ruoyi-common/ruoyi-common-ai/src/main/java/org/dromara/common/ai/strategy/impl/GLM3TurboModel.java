package org.dromara.common.ai.strategy.impl;

import org.dromara.common.ai.strategy.AiModelStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 03/02/2024
 */
@Component("GLM_3_Turbo")
public class GLM3TurboModel implements AiModelStrategy {


    @Override
    public String callApi(SseEmitter sseEmitter,String msg) {
        return msg;
    }
}
