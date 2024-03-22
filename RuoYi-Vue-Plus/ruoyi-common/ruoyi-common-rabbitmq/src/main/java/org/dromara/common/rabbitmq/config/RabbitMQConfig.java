package org.dromara.common.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * <p>
 * description:
 * </p>
 *
 * @author luojiaju
 * @date 28/02/2024
 */
@Slf4j
@Configuration
public class RabbitMQConfig {
    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    @Autowired
    private RabbitProperties properties;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(rabbitConnectionFactory);

        // 并发消费者数量
        containerFactory.setConcurrentConsumers(1);
        containerFactory.setMaxConcurrentConsumers(20);
        // 预加载消息数量 -- QOS
        containerFactory.setPrefetchCount(1);
        // 应答模式（此处设置为手动）
        containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //消息序列化方式
        containerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置通知调用链 （这里设置的是重试机制的调用链）
        containerFactory.setAdviceChain(
            RetryInterceptorBuilder
                .stateless()
                .recoverer(new RejectAndDontRequeueRecoverer())
                .retryOperations(rabbitRetryTemplate())
                .build()
        );
        return containerFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        //默认是用jdk序列化
        //数据转换为json存入消息队列，方便可视化界面查看消息数据
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        //此处设置重试template后，会再生产者发送消息的时候，调用该template中的调用链
        rabbitTemplate.setRetryTemplate(rabbitRetryTemplate());
        rabbitTemplate.setConfirmCallback(
            (correlationData, b, s) -> {
                System.out.println("ConfirmCallback     " + "相关数据：" + correlationData);
                System.out.println("ConfirmCallback     " + "确认情况：" + b);
                System.out.println("ConfirmCallback     " + "原因：" + s);
            });
        //Message message, int i, String s, String s1, String s2
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.out.println("ReturnCallback：     " + "消息：" + returnedMessage.getMessage());
            System.out.println("ReturnCallback：     " + "回应码：" + returnedMessage.getReplyCode());
            System.out.println("ReturnCallback：     " + "回应消息：" + returnedMessage.getReplyText());
            System.out.println("ReturnCallback：     " + "交换机：" + returnedMessage.getExchange());
            System.out.println("ReturnCallback：     " + "路由键：" + returnedMessage.getRoutingKey());
        });


        return rabbitTemplate;
    }

    //重试的Template
    @Bean
    public RetryTemplate rabbitRetryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        // 设置监听  调用重试处理过程
        retryTemplate.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext retryContext, RetryCallback<T, E> retryCallback) {
                // 执行之前调用 （返回false时会终止执行）
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
                // 重试结束的时候调用 （最后一次重试 ）
                System.out.println("---------------最后一次调用");

                return;
            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
                //  异常 都会调用
                System.err.println("-----第{}次调用" + retryContext.getRetryCount());
            }
        });
        retryTemplate.setBackOffPolicy(backOffPolicyByProperties());
        retryTemplate.setRetryPolicy(retryPolicyByProperties());
        return retryTemplate;
    }

    @Bean
    public ExponentialBackOffPolicy backOffPolicyByProperties() {
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        long maxInterval = properties.getListener().getSimple().getRetry().getMaxInterval().getSeconds();
        long initialInterval = properties.getListener().getSimple().getRetry().getInitialInterval().getSeconds();
        double multiplier = properties.getListener().getSimple().getRetry().getMultiplier();
        // 重试间隔
        backOffPolicy.setInitialInterval(initialInterval * 1000);
        // 重试最大间隔
        backOffPolicy.setMaxInterval(maxInterval * 1000);
        // 重试间隔乘法策略
        backOffPolicy.setMultiplier(multiplier);
        return backOffPolicy;
    }

    @Bean
    public SimpleRetryPolicy retryPolicyByProperties() {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        int maxAttempts = properties.getListener().getSimple().getRetry().getMaxAttempts();
        retryPolicy.setMaxAttempts(maxAttempts);
        return retryPolicy;
    }


    @Bean
    public Queue TestDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，有消息者订阅本队列，然后所有消费者都解除订阅此队列，会自动删除。
        // arguments：队列携带的参数，比如设置队列的死信队列，消息的过期时间等等。
        return new Queue("TestDirectQueue", true);
    }

    @Bean
    public DirectExchange TestDirectExchange() {
        // durable:是否持久化,默认是false,持久化交换机。
        // autoDelete:是否自动删除，交换机先有队列或者其他交换机绑定的时候，然后当该交换机没有队列或其他交换机绑定的时候，会自动删除。
        // arguments：交换机设置的参数，比如设置交换机的备用交换机（Alternate Exchange），当消息不能被路由到该交换机绑定的队列上时，会自动路由到备用交换机
        return new DirectExchange("TestDirectExchange", true, false);
    }

    //绑定交换机和队列
    public @Bean
    Binding bindingDirect() {
        //bind队列to交换机中with路由key（routing key）
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("123");
    }
}
