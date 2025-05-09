package com.itheima.reggie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("Reggie 外卖 API 文档")
                        .description("基于 Spring Boot 的 Reggie 外卖项目接口文档")
                        .version("1.0")
                        .build())
                .select()
                // 扫描该包下的所有控制器，生成文档
                .apis(RequestHandlerSelectors.basePackage("com.itheima.reggie.controller"))
                // 配置匹配的 URL 路径
                .paths(PathSelectors.any())
                .build();
    }
}
