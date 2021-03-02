package com.hjy.cloud.common.config.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author liuchun
 * @Package com.hjy.cloud.common.config.rabbitmq
 * @date 2021/3/2 21:59
 * description: 接收者
 */
@Component
@RabbitListener(queues = "liuchun")
public class Receiver {
    //Map<String,Object> rabbitMap
    @RabbitHandler
    public void process(Map<String,Object> rabbitMap){
        System.err.println("receive:"+rabbitMap);
    }
}
