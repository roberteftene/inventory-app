package com.roberteftene.dc.invetory_service.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventory & Order API")
                        .version("1.0")
                        .description("API for managing distribution centers, inventory, orders, and products.")
                        .contact(new Contact().name("Robert Eftene").email("roberteftene25@gmail.com"))
                );
    }
}
