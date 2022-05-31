package com.gmail.arthurstrokov.resume.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
class LoadLink {

    @Bean
    CommandLineRunner initLink() {
        return args -> {
            log.info("http://localhost:8080/swagger-ui.html");
            log.info("http://localhost:8080/actuator");
        };
    }
}
