package com.jpaproject.demo.infra;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI erpLabOpenAPI(){
        return new OpenAPI().info( new Info()
                .title("ERP-LAB API")
                .description("API REST para gerenciamento de clientes, produtos e vendas.")
                .version("v1.0")
                .contact( new Contact()
                        .name("Isac")
                        .url("https://github.com/fantestiQ")
                ));

    }
}
