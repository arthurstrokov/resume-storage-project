package com.gmail.arthurstrokov.resume.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for LoggingAspect
 *
 * @author Arthur Strokov
 */
@ConfigurationProperties("logging.method.exec")
public class LoggingProperties {
    private String loggerName = "AuditLogger";

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }
}
