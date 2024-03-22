package org.dromara.im.handler.ws;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.json.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 15/01/2024
 */
@Slf4j
@Component
//@ServerEndpoint("/im/ws/{userId}/{authentication}")
public class ImWebSocketHandler extends AbstractWebSocketHandler {



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        String authentication = (String) attributes.get("token");
        String loginUser = (String) StpUtil.getLoginIdByToken(authentication);
        if (loginUser == null || loginUser.isEmpty()) {
            session.sendMessage(new TextMessage(Objects.requireNonNull(JsonUtils.toJsonString(R.fail("登录过期了")))));
            session.close();
            return;
        }
        String[] split = loginUser.split(":");
        log.info("websocket连接成功,用户:{},参数:{}", loginUser, attributes);
        WebSocketSessionHolder.addSession(Long.valueOf(split[1]), session);
    }

//    @Override
//    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        log.info("websocket收到消息:{}=>{}", session.getId(), message.getPayload());
//        session.sendMessage(new TextMessage(JsonUtils.toJsonString(R.ok())));
//        super.handleMessage(session, message);
//    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("websocket收到[文本]消息:{}", message.getPayload());
        super.handleTextMessage(session, message);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        log.info("websocket收到[二进制]消息");
        super.handleBinaryMessage(session, message);
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        log.info("websocket收到[pong]消息");
        super.handlePongMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.info("websocket连接发生错误");
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("websocket连接已关闭");
        super.afterConnectionClosed(session, status);
    }

    @Override
    public boolean supportsPartialMessages() {
        log.info("websocket开始支持部分消息");
        return super.supportsPartialMessages();
    }


}
