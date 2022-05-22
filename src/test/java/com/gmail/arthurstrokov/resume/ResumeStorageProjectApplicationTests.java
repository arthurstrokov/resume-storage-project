package com.gmail.arthurstrokov.resume;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ResumeStorageProjectApplicationTests {
    @Autowired
    ApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context); // TODO How make database test in docker?
    }
}
