package com.hjy.cloud.t_system.controller;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.t_system.service.HystrixUserService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
//@DefaultProperties(defaultFallback ="consumer_global_fallback")
public class HystrixUserController {

    @Resource
    private HystrixUserService hystrixUserService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/consumer/provider/rszy/group")
    public CommonResult insertPage(){
        return hystrixUserService.insertPage();
    }
    @GetMapping(value = "/consumer/provider/rszy/bc")
    public CommonResult insertPagebc(){
        return hystrixUserService.insertPagebc();
    }
//    @HystrixCommand(fallbackMethod = "consumer_fallback_consumerSystemUserList",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "20000")
//    })
//    @HystrixCommand
//    @PostMapping(value = "/consumer/system/user/list")
//    public CommonResult consumerSystemUserList(@RequestBody String param){
//        return hystrixUserService.consumerSystemUserList(param);
//    }
//    @GetMapping(value = "/consumer/payment/hystrix/error/{username}")
//    public String paymentInfo_timeout(@PathVariable("username") String username){
//        return hystrixUserService.paymentInfo_timeout2(username);
//    }
    /**
     * @HystrixCommand开启服务降级，对应主启动类注解@EnableCircuitBreaker//激活服务降级
     * 服务降级示例
     *出错访问，error的方法
     * @return
     */
//    @HystrixCommand(fallbackMethod = "consumer_paymentInfo_timeout_fallback",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "6000")
//    })
//    @HystrixCommand
//    @GetMapping(value = "/consumer/payment/hystrix/error/{username}")
//    public String paymentInfo_timeout2(@PathVariable("username") String username){
//        int a = 10/0 ;
//        return hystrixUserService.paymentInfo_timeout2(username);
//    }
//    //当某服务（consumerSystemUserList）失败，就会调用兜底的fallback方法
//    public CommonResult consumer_fallback_consumerSystemUserList() {
//        return new CommonResult(444,"error","serverPort:"+serverPort+",我是消费者8083，支付系统繁忙，请稍后再试o(╥﹏╥)o",null);
//    }
//    //下面是全局fallback
//    public CommonResult consumer_global_fallback() {
//        return new CommonResult(444,"error","serverPort:"+serverPort+",我是消费者8083,global全局异常处理信息o(╥﹏╥)o",null);
//
//    }
//    @PostMapping(value = "/consumer/lj")
//    public CommonResult lj(){
//        return hystrixUserService.lj();
//    }
}
