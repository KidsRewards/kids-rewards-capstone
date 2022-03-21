package com.codeup.kidsrewardscapstone.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class addResourceHandlers implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry
                .addResourceHandler("/img/**")
                .addResourceLocations("file:resources/" , "file:uploads/", "file:/img/**")
                .setCachePeriod(0);
    }
}
