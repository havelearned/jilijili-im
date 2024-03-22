package org.dromara.im.controller;

import lombok.RequiredArgsConstructor;
import org.dromara.common.ai.config.properties.AiProperties;
import org.dromara.common.ai.strategy.context.AIModelContext;
import org.dromara.common.ai.strategy.type.AiModelType;
import org.dromara.common.core.domain.R;
import org.dromara.im.domain.ChatMessage;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * 测试控制器
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 26/01/2024
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final AiProperties aiProperties;

    private final AIModelContext aiModelContext;


    @MessageMapping("/welcome") // 浏览器发送请求通过@messageMapping 映射/welcome 这个地址。
    @SendTo("/topic/getResponse") //服务器端有消息时,会订阅@SendTo中的路径的浏览器发送消息。
    public R<?> say(ChatMessage message) throws Exception {
//        template.convertAndSend("/topic/getResponse", message);
        return R.ok(message);
    }

    @GetMapping("/oauth/testTopic")
    public void testTopic() {
//        template.convertAndSend("/topic/getResponse", "testTopic");
    }


    @GetMapping("/aiconfig")
    public R<?> aiconfig() {
/*        String apiKey = aiProperties.getId();
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + 1000 * 60 * 60; // 设置过期时间为1小时后
        Date now = new Date(nowMillis);
        Date exp = new Date(expMillis);
        Map<String, Object> heandmap = new HashMap<>();
        heandmap.put("alg", "HS256");
        heandmap.put("sign_type", "SIGN");
        Map<String, Object> payLoadmap = new HashMap<>();
        payLoadmap.put("api_key", apiKey);
        payLoadmap.put("exp", exp.getTime());
        payLoadmap.put("timestamp", now.getTime());
        String token = JWTUtil.createToken(heandmap, payLoadmap, aiProperties.getSecret().getBytes());

        boolean verify = JWTUtil.verify(token, aiProperties.getSecret().getBytes());*/
//        String hello = aiModelContext.callAPI(AiModelType.GML_4.getModel(), "hello");
        return R.ok();
    }


    /**
     * 测试AI 模型 SSE
     *
     * @return
     */
    @PostMapping(value = "/message", produces = MediaType.TEXT_PLAIN_VALUE)
    public SseEmitter handleMessage(@RequestBody String msg) {
        SseEmitter sseEmitter = new SseEmitter();
        aiModelContext.callAPI(AiModelType.GML_4.getModel(), sseEmitter, msg);
        return sseEmitter;
    }

    @GetMapping("/genToken")
    public String genToken() {
        return "";
    }


//    private final RabbitTemplate rabbitTemplate;
//
//    @GetMapping("/mq_test")
//    public String mq_test() {
//        String messageId = UUID.randomUUID().toString();
//        String messageData = "test message,hello!";
//        String current = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        Map<String, Object> map = new HashMap<>();
//        map.put("messageId", messageId);
//        map.put("data", messageData);
//        map.put("current", current);
//        rabbitTemplate.convertAndSend("TestDirectExchange", "123", map, new CorrelationData(UUID.randomUUID().toString()));
//
//        return "Ok";
//    }


}
