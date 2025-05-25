package com.example.health_connection.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Value("http://localhost:8080")
    private String appUrl;

    @Bean
    OpenAPI customOpenApi() {
        return new OpenAPI()
                    .addServersItem(new Server().url(appUrl))
                    .info(new Info().title("Health Connection Backend").version("1.00")
                        .description("This is the swaggerDoc for backend project of Health Connection"))
                    .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("bearer")
                            .bearerFormat("JWT")))
                    .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
