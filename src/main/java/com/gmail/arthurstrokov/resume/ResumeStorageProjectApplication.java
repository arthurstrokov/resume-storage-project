package com.gmail.arthurstrokov.resume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Project that present logic for storage resumes using JSON
 *
 * @author Arthur Strokov
 * @version 1.0.0
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ResumeStorageProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeStorageProjectApplication.class, args);
    }
}
