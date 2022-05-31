package com.gmail.arthurstrokov.resume.configuration;

import com.gmail.arthurstrokov.resume.aop.LoggingAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure Logging aspect creation Bean
 *
 * @author Arthur Strokov
 */
@Configuration
@ConditionalOnClass(LoggingAspect.class)
@EnableConfigurationProperties(LoggingProperties.class)
public class LoggingAspectAutoConfigure {
    private final LoggingProperties properties;

    public LoggingAspectAutoConfigure(LoggingProperties properties) {
        this.properties = properties;
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect(properties.getLoggerName());
    }
}
