package com.hjy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author liuchun
 * @createDate 2020/10/26 15:51
 * @Classname OrderHystrixMain80
 * @Description TODO
 */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class OrderHystrixMain8083 {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain8083.class,args);
    }

}
