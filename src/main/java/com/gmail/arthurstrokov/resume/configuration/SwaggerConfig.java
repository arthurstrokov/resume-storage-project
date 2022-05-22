package com.gmail.arthurstrokov.resume.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration
 *
 * @author Arthur Strokov
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Resume storage project")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("arthurstrokov@gmail.com")
                                                .name("Arthur Strokov")
                                )
                );
    }
}
