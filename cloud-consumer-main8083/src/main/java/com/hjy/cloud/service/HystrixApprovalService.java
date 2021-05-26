//package com.hjy.cloud.service;
//
//import com.hjy.cloud.domin.CommonResult;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//
///**
// * @author liuchun
// * @Package com.hjy.cloud.service
// * @date 2021/5/25 14:13
// * description:
// */
//@Component
//@FeignClient(value = "CLOUD-PROVIDER-SERVICE")//feign客户端微服务名称
//public interface HystrixApprovalService {
//
//    @GetMapping(value = "/apv/approval/addPage")
//    CommonResult insertPage();
//}
