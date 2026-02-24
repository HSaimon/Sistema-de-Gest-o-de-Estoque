package com.estoque.sistema.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Sistema de Gestão de Estoque API")
                .version("1.0.0")
                .description("API para gestão de produtos, pedidos e relatórios em tempo real")
                .contact(new Contact().name("Equipe Estoque").email("contato@estoque.com")));
    }
}
