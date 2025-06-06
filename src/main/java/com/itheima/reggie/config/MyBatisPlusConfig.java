package com.itheima.reggie.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor myBatisPlusInterceptor = new MybatisPlusInterceptor();
        myBatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return myBatisPlusInterceptor;
    }
}
