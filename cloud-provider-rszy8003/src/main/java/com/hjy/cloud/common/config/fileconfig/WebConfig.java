package com.hjy.cloud.common.config.fileconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liuchun
 * @createDate 2020/11/4 10:11
 * @Classname WebConfig
 * @Description TODO
 * .addResourceLocations("file:D:/hjy/ywjg/zzjgdm")
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {
        // 在D:/res/pic下如果有一张 luffy.jpg.jpg的图片，那么：
        // 1 访问：http://localhost:8080/img/luffy.jpg 可以访问到
        // 2 html 中 <img src="http://localhost:8080/img/luffy.jpg">
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/img/**")
                    .addResourceLocations("file:D:/hjy/rszy/picture_file/icon/")
                    .addResourceLocations("file:D:/hjy/rszy/picture_file/other/");
        }
}
