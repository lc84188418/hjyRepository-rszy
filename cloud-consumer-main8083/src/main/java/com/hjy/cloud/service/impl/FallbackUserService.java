//package com.hjy.cloud.service.impl;
//
//import com.hjy.cloud.domin.CommonResult;
//import com.hjy.cloud.service.HystrixUserService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
///**
// * @author liuchun
// * @createDate 2020/10/28 9:37
// * @Classname PaymentHystrixServiceImpl
// * @Description 为实现的接口PaymentHystrixService 里的每一个方法写fallback方法
// */
//
//@Component
//public class FallbackUserService implements HystrixUserService {
//    @Value("${server.port}")
//    private String serverPort;
//    @Override
//    public CommonResult consumerSystemUserList(String param) {
//        return new CommonResult(407,"error","serverPort:"+serverPort+",这里是FallbackUserService",null);
//    }
//
//    @Override
//    public CommonResult lj() {
//        return new CommonResult(407,"error","serverPort:"+serverPort+",这里是FallbackUserService-lj",null);
//
//    }
//
////    @Override
////    public String paymentInfo_timeout2(String username) {
////        return "PaymentFallbackService fall back-paymentInfo_timeout2";
////    }
//}
