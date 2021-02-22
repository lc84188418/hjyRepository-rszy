package com.hjy.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author liuchun
 * @createDate 2020/11/12 22:37
 * @Classname EurekaServer7001
 * @Description TODO
 */

@SpringBootApplication
@EnableEurekaServer//代表服务注册中心
public class EurekaServer7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer7001.class,args);
    }
}