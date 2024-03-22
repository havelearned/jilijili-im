package org.dromara.common.ai.strategy.context;

import lombok.AllArgsConstructor;
import org.dromara.common.ai.config.properties.AiProperties;
import org.dromara.common.ai.strategy.AiModelStrategy;
import org.dromara.common.ai.strategy.type.AiModelType;
import org.dromara.common.core.utils.SpringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 03/02/2024
 */
@Component("aiModelContext")
@AllArgsConstructor
public class AIModelContext {

    private Map<String, AiModelStrategy> modelStrategyMap;


    public String callAPI(String model, SseEmitter sseEmitter, String msg) {
        AiModelStrategy aiModelStrategy = modelStrategyMap.get(model);
        return aiModelStrategy.callApi(sseEmitter, msg);
    }

    public String callAPI(SseEmitter sseEmitter, String msg) {
        String defModel = SpringUtils.getBean(AiProperties.class).getDefModel();
        AiModelStrategy aiModelStrategy = modelStrategyMap.get(AiModelType.getModelByParam(defModel));
        return aiModelStrategy.callApi(sseEmitter, msg);
    }


}
