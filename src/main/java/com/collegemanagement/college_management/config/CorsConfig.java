package com.collegemanagement.college_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:3000",  // Allow local frontend
                                "https://newproject-three-inky.vercel.app"  // Allow Vercel frontend
                        )
                        .allowedMethods("*")  // Allow all HTTP methods
                        .allowedHeaders("*")  // Allow all headers
                        .allowCredentials(true);
            }
        };
    }
}
