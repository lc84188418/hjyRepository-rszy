package com.hjy.cloud.t_system.service;

import com.hjy.cloud.t_system.service.impl.PaymentFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author liuchun
 * @createDate 2020/10/26 15:54
 * @Classname PaymentHystrixService
 * @Description TODO
 */
@Component
@FeignClient(value = "CLOUD-CONSUMER-SERVICE" ,fallback = PaymentFallbackService.class)
public interface PaymentHystrixService {

    @GetMapping(value = "/system/user/addPage")
    public String consumerUserAddPage();

//    @GetMapping(value = "/payment/hystrix/error/{username}")
//    public String paymentInfo_timeout2(@PathVariable("username") String username);
}
