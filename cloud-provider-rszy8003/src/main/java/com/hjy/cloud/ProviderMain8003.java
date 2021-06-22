package com.hjy.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author liuchun
 * @createDate 2020/11/12 22:45
 * @Classname HJYSystemApplication
 * @Description TODO
 */

@MapperScan("com.hjy.cloud.*.dao")
@EnableAsync  //开启异步注解功能
@EnableScheduling   //开启基于注解的定时任务
@EnableCaching //开启基于注解的缓存
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient//服务发现
public class ProviderMain8003 {
    public static void main(String[] args) {
        SpringApplication.run(ProviderMain8003.class, args);
    }

}
