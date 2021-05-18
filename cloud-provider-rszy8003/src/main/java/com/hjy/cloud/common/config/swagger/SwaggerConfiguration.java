//package com.hjy.cloud.common.config.swagger;
//
//import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @description:
// * @author: youcong
// * @time: 2020/11/14 15:46
// */
//@Configuration
//@EnableSwagger2
//@EnableSwaggerBootstrapUI
//@ConditionalOnProperty(prefix = "swagger", value = {"enable"}, havingValue = "true")
//public class SwaggerConfiguration {
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .ignoredParameterTypes(HttpSession.class, HttpServletRequest.class, HttpServletResponse.class)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .apis(RequestHandlerSelectors.basePackage("com.hjy.cloud"))
//                .paths(PathSelectors.any())
//                .build()
//                .globalOperationParameters(globalOperation());
//    }
//
//    /**
//     * 自定义全局入参
//     */
//    private List<Parameter> globalOperation() {
//        //添加head参数配置start
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        //第一个token为传参的key，第二个token为swagger页面显示的值
//        tokenPar.name("token").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());
//        return pars;
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("人事资源管理系统-API文档")
//                .description("人事资源管理系统接口文档")
//                .termsOfServiceUrl("http://localhost:8003/")
//                .version("1.0")
//                .build();
//    }
//}