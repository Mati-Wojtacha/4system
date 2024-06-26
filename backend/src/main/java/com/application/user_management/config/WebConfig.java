package com.application.user_management.config;

import com.application.user_management.converters.SortCriteriaArrayConverter;
import com.application.user_management.converters.SortCriteriaSingleValueConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final SortCriteriaArrayConverter sortCriteriaArrayConverter;
    private final SortCriteriaSingleValueConverter sortCriteriaSingleValueConverter;

    public WebConfig(SortCriteriaArrayConverter sortCriteriaArrayConverter, SortCriteriaSingleValueConverter sortCriteriaSingleValueConverter) {
        this.sortCriteriaArrayConverter = sortCriteriaArrayConverter;
        this.sortCriteriaSingleValueConverter = sortCriteriaSingleValueConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(sortCriteriaArrayConverter);
        registry.addConverter(sortCriteriaSingleValueConverter);
    }
}