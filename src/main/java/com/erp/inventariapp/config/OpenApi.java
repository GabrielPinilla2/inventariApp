package com.erp.inventariapp.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Documentación de APIs de InventariApp",
        version = "1.0.0",
        description = "El presente es el documento es la especificación de las APIs de InventariApp"
    )
)
public class OpenApi {

}
