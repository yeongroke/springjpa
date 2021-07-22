package com.kyr.springjpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        CacheControl cacheControl = CacheControl
                .maxAge(60, TimeUnit.SECONDS)
                .mustRevalidate();

        registry.addResourceHandler("**/*")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(cacheControl)
        ;
    }
}
