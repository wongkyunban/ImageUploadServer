package com.wong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer  implements WebMvcConfigurer {
    /**
     * 资源映射路径
     */

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/recFiles/**").addResourceLocations("/home/kyun/Downloads/recFiles/");
    }
}
