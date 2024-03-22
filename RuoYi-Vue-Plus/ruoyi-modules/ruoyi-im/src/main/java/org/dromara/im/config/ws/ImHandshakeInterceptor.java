package org.dromara.im.config.ws;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Map;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 27/01/2024
 */
public class ImHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        URI uri = request.getURI();
        getParam(uri, attributes);
        return true;

    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }


    public void getParam(URI uri, Map<String, Object> attributes) throws MalformedURLException {
        String query = uri.toURL().getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    attributes.put(key, value);
                }
            }
        }
    }
}
