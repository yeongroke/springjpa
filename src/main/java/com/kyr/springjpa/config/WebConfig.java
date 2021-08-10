package com.kyr.springjpa.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${serverurl.resource.path}")
    private String resourceFilePath;

    @Value("${serverurl.request.path}")
    private String requestPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        log.info("requestpath-> " + requestPath);
        log.info("resourcesfilepath->" + resourceFilePath);

        CacheControl cacheControl = CacheControl
                .noCache();


        registry.addResourceHandler("**/*")
                .addResourceLocations("classpath:/static/")
                .setCacheControl(cacheControl);

        registry.addResourceHandler(requestPath)
                .addResourceLocations(resourceFilePath);
    }
}
