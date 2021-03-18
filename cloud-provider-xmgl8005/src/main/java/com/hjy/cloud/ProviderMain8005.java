package com.hjy.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liuchun
 * @Package com.hjy.cloud
 * @date 2021/3/18 23:04
 * description:
 */
@MapperScan("com.hjy.cloud.*.dao")
@EnableAsync  //开启异步注解功能
@EnableScheduling   //开启基于注解的定时任务
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient//服务发现
public class ProviderMain8005 {
    public static void main(String[] args) {
        SpringApplication.run(ProviderMain8005.class, args);
    }

}
