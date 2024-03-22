package org.dromara.common.ai.strategy.impl;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.ai.config.properties.AiProperties;
import org.dromara.common.ai.domain.bo.ModelBo;
import org.dromara.common.ai.domain.bo.ModelMessage;
import org.dromara.common.ai.strategy.AiModelStrategy;
import org.dromara.common.ai.strategy.type.AiModelType;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dromara.common.ai.constant.AiStringConstantKey.MODEL_TOKEN;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 03/02/2024
 */
@Slf4j
@Component("GLM_4")
@RequiredArgsConstructor
public class GLM4Model implements AiModelStrategy {
    private final AiProperties aiProperties;

    @Override
    public String callApi(SseEmitter sseEmitter, String msg) {
        // 得到当前策略ai模型
        AiProperties.AiModel configModel = getCurrentModel();

        // 构建POST请求
        HttpRequest post = HttpUtil.createPost(configModel.getUrl());

        // 设置请求头
        Map<String, String> headers = Map.of("Authorization",
            genToken(MODEL_TOKEN.formatted(AiModelType.GML_4.getModel()), configModel));
        post.addHeaders(headers);

        // 添加参数
        ModelBo param = new ModelBo();
        param.setModel(AiModelType.GML_4.getParam());
        param.setMessages(List.of(ModelMessage.builder().role("user").content(msg).build()));
        param.setMax_tokens(1024);

        String jsonParam = JsonUtils.toJsonString(param);
        post.body(jsonParam);
        log.info("请求参数=>{}", jsonParam);

        // 接收ai模型返回的数据,注意是流式响应SSE
        HttpResponse response = post.execute();
        try (InputStream is = response.bodyStream()) {
            byte[] b = new byte[1024];
            int len;
            while ((len = is.read(b)) != -1) {
                String line = new String(b, 0, len);
                // 处理event stream数据
                sseEmitter.send(line);
            }
        } catch (IOException e) {
            // 更具体的异常处理
            throw new RuntimeException("Error reading response body", e);
        } finally {
            sseEmitter.complete();
        }
        return msg;
    }

    private AiProperties.AiModel getCurrentModel() {
        return aiProperties.getConfigModel(AiModelType.GML_4.getModel());
    }

    /**
     * 生成token
     *
     * @return 没有就重新生成
     */

    @PostConstruct
    public String checkToken() {
        AiProperties.AiModel mode = aiProperties.getConfigModel(AiModelType.GML_4.getModel());
        String key = MODEL_TOKEN.formatted(AiModelType.GML_4.getModel());
        String token = RedisUtils.getCacheObject(key);

        if (token != null) {
            JWT jwt = JWTUtil.parseToken(token);
            JWTPayload jwtPayload = jwt.getPayload();
            long expTime = ((NumberWithFormat) jwtPayload.getClaim("exp")).longValue();
            if (JWTUtil.verify(token, mode.getSecret().getBytes()) && System.currentTimeMillis() <= expTime) {
                return token;
            }
        }
        log.info("ai token 过期重新生成:{}", mode.getName());
        return genToken(key, mode);
    }

    private static String genToken(String key, AiProperties.AiModel mode) {
        RedisUtils.deleteKeys(key);
        String apiKey = mode.getApiKey();
        // 1 hour expiration
        long expMillis = System.currentTimeMillis() + 1000 * 60 * 60;
        Date exp = new Date(expMillis);
        Map<String, Object> headers = new HashMap<>(Map.of("alg", "HS256", "sign_type", "SIGN"));
        Map<String, Object> payload = new HashMap<>(Map.of("api_key", apiKey, "exp", exp.getTime(), "timestamp", System.currentTimeMillis()));
        String cacheToken = JWTUtil.createToken(headers, payload, mode.getSecret().getBytes());
        RedisUtils.setCacheObject(key, cacheToken);
        return cacheToken;
    }


}
