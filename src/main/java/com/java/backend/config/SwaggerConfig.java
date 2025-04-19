package com.java.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Docs",
                description = "API 문서",
                version = "v1.0"
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "default url")
        }
)
public class SwaggerConfig {

}
