package org.dromara.im.enums;

import lombok.Getter;

@Getter
public enum ChatType {

    /**
     * 单聊
     */
    SINGLE("single"),

    /**
     * 群聊
     */
    GROUP("group"),

    /**
     * 频道
     */
    CHANNEL("channel"),

    /**
     * 订阅
     */
    SUBSCRIBE("subscribe"),


    PING("ping"),

    PONG("pong"),
    ;

    private final String type;

    ChatType(String type) {
        this.type = type;
    }

    public static ChatType getByType(String type) {
        for (ChatType chatType : ChatType.values()) {
            if (chatType.getType().equals(type)) {
                return chatType;
            }
        }
        // 默认ping
        return PING;
    }
}
