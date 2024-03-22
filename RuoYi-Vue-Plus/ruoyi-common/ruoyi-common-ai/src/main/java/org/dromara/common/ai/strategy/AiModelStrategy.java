package org.dromara.common.ai.strategy;


import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface AiModelStrategy {

    String callApi(SseEmitter emitter, String msg);

}
