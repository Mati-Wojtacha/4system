package com.application.user_management.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("UserManagement Application API")
                        .version("v0.0.1"));
    }
}
