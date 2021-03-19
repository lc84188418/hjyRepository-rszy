package com.hjy.cloud.common.config.rabbitmq;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuchun
 * @Package com.lc.homepage.controller
 * @date 2021/3/10 22:35
 * description:
 */
@RestController
public class SendMessageController {

    @Resource
    private IMessageProvider messageProvider;
    @GetMapping("/sendMessage")
    public String sendMessage(){
        return messageProvider.send();
    }
}
