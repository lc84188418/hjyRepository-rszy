package com.hjy.cloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuchun
 * @createDate 2020/10/26 11:05
 * @Classname FeignConfig
 * @Description TODO
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level feignLoggerLevel(){
       return Logger.Level.FULL;
    }
}
