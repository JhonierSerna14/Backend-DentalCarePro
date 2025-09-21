package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para DentalCarePro
 * 
 * Acceso a la documentación:
 * - Swagger UI: http://localhost:8080/docs
 * - API Docs JSON: http://localhost:8080/api-docs
 * 
 * @author DentalCarePro Team
 * @version 1.0
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI dentalCareProOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("DentalCarePro API")
                        .description("Sistema de Gestión de Clínica Odontológica - API REST completa para la administración de pacientes, odontólogos, citas y tratamientos dentales")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("DentalCarePro Team")
                                .email("support@dentalcarepro.com")
                                .url("https://github.com/JhonierSerna14/Backend-DentalCarePro"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor de Desarrollo"),
                        new Server().url("https://api.dentalcarepro.com").description("Servidor de Producción")))
                .components(new Components()
                        .addSecuritySchemes("session-auth", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .name("JSESSIONID")
                                .description("Autenticación basada en sesión")))
                .addSecurityItem(new SecurityRequirement().addList("session-auth"));
    }
}