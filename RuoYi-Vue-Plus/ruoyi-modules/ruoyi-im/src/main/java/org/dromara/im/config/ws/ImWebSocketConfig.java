package org.dromara.im.config.ws;

import lombok.extern.slf4j.Slf4j;
import org.dromara.im.handler.ws.ImWebSocketHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 15/01/2024
 */
@Configuration
@EnableWebSocket
@Slf4j
public class ImWebSocketConfig implements WebSocketConfigurer {
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//
//        return new ServerEndpointExporter();
//    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), "/im-websocket")
            .addInterceptors(imHandshakeInterceptor())
            .setAllowedOriginPatterns("*");
    }

    public WebSocketHandler myWebSocketHandler() {
        return new ImWebSocketHandler();
    }

    // 拦截器
    public HandshakeInterceptor imHandshakeInterceptor() {
        return new ImHandshakeInterceptor();
    }


//    @Bean
//    public WebSocketHandler imWebSocketHandler() {
//        return new ImWebSocketHandler();
//    }


//
//
//    @Bean
//    public HandshakeHandler imHandshakeHandler() {
//        return new ImHandshakeHandler();
//    }


}
