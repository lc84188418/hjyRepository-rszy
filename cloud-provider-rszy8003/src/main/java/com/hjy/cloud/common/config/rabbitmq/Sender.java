package com.hjy.cloud.common.config.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author liuchun
 * @Package com.hjy.cloud.common.config.rabbitmq
 * @date 2021/3/2 21:54
 * description: 发送者
 */
@Component
public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String routingKey,Map<String,Object> rabbitMap){
        rabbitTemplate.convertAndSend(routingKey,rabbitMap);
    }
}
