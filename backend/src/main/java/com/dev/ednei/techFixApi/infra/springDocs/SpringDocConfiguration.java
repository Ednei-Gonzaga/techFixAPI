package com.dev.ednei.techFixApi.infra.springDocs;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    private String textApi = "A TechFix API é uma aplicação RESTfull (atualmente em desenvolvimento) pensada para fornecer uma solução completa de organização e gerenciamento no conserto de aparelhos eletrônicos para assistências técnicas. O objetivo é permitir que a loja tenha controle total no registro de clientes, funcionários e serviços. Além disso, o sistema permite que os clientes acompanhem o status de seus dispositivos desde o balcão até a entrega final, e auxilia na organização da ordem em que os técnicos irão realizar as manutenções na bancada.";
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title("TechFix API")
                        .description(textApi)
                        .contact(new Contact()
                                .name("Time Backend")
                                .email("backend@techfix.com"))
                        .version("1.0.0"))
                ;
    }
}
