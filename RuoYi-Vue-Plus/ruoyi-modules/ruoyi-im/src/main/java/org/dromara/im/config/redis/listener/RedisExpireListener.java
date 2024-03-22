package org.dromara.im.config.redis.listener;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.redis.utils.RedisUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 12/03/2024
 */
@Slf4j
@Component
public class RedisExpireListener extends KeyExpirationEventMessageListener {
    public RedisExpireListener(RedisMessageListenerContainer redisMessageListenerContainer) {
        super(redisMessageListenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        System.out.println("Key -> "+expiredKey +"过期了...");
        RedisUtils.deleteObject(message.toString());
    }
}
