package br.dev.ltres.poc.integrateams.interfaces.rest.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Integra Teams", version = "v1", description = "Integração de cadastro local de eventos com o Microsoft Teams", contact = @Contact(name = "Luciano", email = "lucianotres@outlook.com")))
public class OpenApiConfig {
}
