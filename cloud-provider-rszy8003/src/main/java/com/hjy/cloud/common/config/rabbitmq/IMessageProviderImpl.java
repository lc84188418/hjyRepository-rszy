package com.hjy.cloud.common.config.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author liuchun
 * @Package com.lc.homepage.service.impl
 * @date 2021/3/10 22:23
 * description:
 */
@EnableBinding(Source.class) //定义消息的推送管道
public class IMessageProviderImpl implements IMessageProvider {

    //消息发送管道
    @Resource
    private MessageChannel output;

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",serial);
        output.send(MessageBuilder.withPayload(jsonObject).build());
        System.out.println("****serial"+jsonObject);
        return serial;
    }
}
