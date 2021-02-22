package com.hjy.cloud.t_system.controller;

import com.hjy.cloud.t_system.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuchun
 * @createDate 2020/10/26 16:00
 * @Classname PaymentHystrixController
 * @Description TODO
 */
@RestController
@Slf4j
@DefaultProperties(defaultFallback ="consumer_global_fallback")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "consumer/system/user/addPage")
    public String consumerUserAddPage(){
        return paymentHystrixService.consumerUserAddPage();
    }
//    @GetMapping(value = "/consumer/payment/hystrix/error/{username}")
//    public String paymentInfo_timeout(@PathVariable("username") String username){
//        return paymentHystrixService.paymentInfo_timeout2(username);
//    }
    /**
     * @HystrixCommand开启服务降级，对应主启动类注解@EnableCircuitBreaker//激活服务降级
     * 服务降级示例
     *出错访问，error的方法
     * @param username
     * @return
     */
//    @HystrixCommand(fallbackMethod = "consumer_paymentInfo_timeout_fallback",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "6000")
//    })
//    @HystrixCommand
//    @GetMapping(value = "/consumer/payment/hystrix/error/{username}")
//    public String paymentInfo_timeout2(@PathVariable("username") String username){
//        int a = 10/0 ;
//        return paymentHystrixService.paymentInfo_timeout2(username);
//    }
//    //当某服务（paymentInfo_timeout2）失败，就会调用兜底的fallback方法
//    public String consumer_paymentInfo_timeout_fallback(String username) {
//        return "我是消费者80，支付系统繁忙，请稍后再试o(╥﹏╥)o";
//    }
//    //下面是全局fallback
//    public String consumer_global_fallback() {
//        return "线程池: "+Thread.currentThread().getName()+" 我是消费者80,global全局异常处理信息，"+"o(╥﹏╥)o";
//    }
}
