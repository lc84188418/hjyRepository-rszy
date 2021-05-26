package com.hjy.cloud.controller;

import com.hjy.cloud.domin.CommonResult;
import com.hjy.cloud.service.HystrixUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuchun
 * @Package com.hjy.cloud.controller
 * @date 2021/5/25 14:12
 * description:
 */
@RestController
@Slf4j
public class HystrixApprovalController {

    @Value("${server.port}")
    private String serverPort;
//    @Resource
//    private HystrixApprovalService approvalService;
    @Resource
    private HystrixUserService hystrixUserService;
    /**
     * 1 跳转到新增页面
     */
    @GetMapping(value = "/consumer/apv/approval/addPage")
    public CommonResult insertPage(){
        return hystrixUserService.apvApprovalInsertPage();
    }
}
