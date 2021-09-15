package com.pratap.item.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
//                .addMapping("/**")
                .addMapping("/items/{itemId}")
                .allowedMethods("GET")
                .allowedOrigins("http://localhost:8080", "http://localhost:8081");
    }
}
