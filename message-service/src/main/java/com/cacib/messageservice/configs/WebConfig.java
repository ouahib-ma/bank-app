package com.cacib.messageservice.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Ouahib
 * @Date 02/12/2024
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String[] ALLOWED_ORIGINS = { "http://localhost:4200" };
    private static final String[] ALLOWED_METHODS = { "GET", "POST", "PUT", "DELETE", "OPTIONS" };
    private static final String API_PATH_PATTERN = "/**";
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(API_PATH_PATTERN)
                .allowedOrigins(ALLOWED_ORIGINS)
                .allowedMethods(ALLOWED_METHODS)
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Bean
    public OpenAPI partnerAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Message Service API")
                        .version("1.0")
                        .description("API pour la gestion des messages."));
    }
}
