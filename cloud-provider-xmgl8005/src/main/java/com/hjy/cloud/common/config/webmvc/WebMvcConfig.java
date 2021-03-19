package com.hjy.cloud.common.config.webmvc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path.win}")
    public String uploadedFilePathPrefixForWin;
    @Value("${file.upload.path.other}")
    public String uploadedFilePathPrefixForOther;

    private static final String FILE_PROTOCOL = "file:///";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations(FILE_PROTOCOL + uploadedFilePathPrefixForWin);
        } else {
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations(FILE_PROTOCOL+uploadedFilePathPrefixForOther);   //媒体资源
        }
    }

}


