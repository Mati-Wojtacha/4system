package com.application.user_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    private final String corsOriginUrls;

    public CorsConfig(@Value("${CORS_ORIGIN_URLS}") String corsOriginUrls) {
        if (corsOriginUrls == null || corsOriginUrls.isEmpty()) {
            throw new RuntimeException("CORS_ORIGIN_URLS environment variable is not set");
        }

        this.corsOriginUrls = corsOriginUrls;
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(corsOriginUrls.split(","))
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(3600L)
                .allowedHeaders("*");
    }
}