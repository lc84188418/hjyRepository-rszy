package com.hjy.cloud.common.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author liuchun
 * @Package com.hjy.cloud.common.config.rabbitmq
 * @date 2021/3/2 21:51
 * description: RabbitMq配置
 */
@Configuration
public class RabbitConfig {
    /**
     * 队列配置
     * @return
     */
    @Bean
    public Queue helloQueue(){
        return new Queue("liuchun");
    }
    /**
     * 转换器
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
