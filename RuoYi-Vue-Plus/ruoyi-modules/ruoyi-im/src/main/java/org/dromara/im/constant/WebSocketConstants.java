package org.dromara.im.constant;

/**
 * websocket的常量配置
 *
 * @author zendwang
 */
public interface WebSocketConstants {

    /**
     * 用户在线的key
     */
    String IM_ONLINE_USER = "IM_ONLINE_USER";

    /**
     * 群组的key
     */
    String IM_GROUP_USER_KEY = "IM_GROUP_USER_KEY";


    /**
     * 前端心跳检查的命令
     */
    String PING = "ping";

    /**
     * 服务端心跳恢复的字符串
     */
    String PONG = "pong";
}
