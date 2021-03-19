package com.hjy.cloud.t_system.service;

import com.hjy.cloud.domin.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author liuchun
 * @createDate 2020/10/26 15:54
 * @Classname PaymentHystrixService
 * @Description TODO
 *  ,fallback = FallbackUserService.class
 */
@Component
//@FeignClient(value = "CLOUD-CONSUMER-SERVICE")//feign客户端微服务名称
@FeignClient(value = "CLOUD-PROVIDER-SERVICE")//feign客户端微服务名称
public interface HystrixUserService {

//    @PostMapping(value = "/system/user/list")
//    CommonResult consumerSystemUserList(String param);
//    @PostMapping(value = "/consumer/lj")
//    CommonResult lj();

//    @GetMapping(value = "/payment/hystrix/error/{username}")
//    public String paymentInfo_timeout2(@PathVariable("username") String username);
    @GetMapping(value = "/provider/rszy/group")
    CommonResult insertPage();
    @GetMapping(value = "/provider/rszy/bc")
    CommonResult insertPagebc();
}
