package com.challenge.empresas.config;


import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;

@Configuration
@ComponentScan(basePackages = "com.challenge.empresas.adapter.in.web")
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de Empresas")
                        .description("API para la gestión de empresas")
                        .version("1.0"));
    }
}
