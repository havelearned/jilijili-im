package org.dromara;

import org.dromara.common.redis.utils.RedisUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.api.RSearch;
import org.redisson.api.RedissonClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

import java.util.Collection;

/**
 * 启动程序
 *
 * @author Lion Li
 */

@SpringBootApplication
public class DromaraApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DromaraApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  RuoYi-Vue-Plus启动成功   ლ(´ڡ`ლ)ﾞ");


    }


    @Override
    public void run(String... args) throws Exception {
        Collection<String> loginKey = RedisUtils.keys("Authorization:login:*");
        for (String key : loginKey) {
            RedisUtils.deleteObject(key);
        }
        Collection<String> onlineKey = RedisUtils.keys("online_tokens:*");
        for (String s : onlineKey) {
            RedisUtils.deleteObject(s);
        }
    }
}
