package com.gmail.arthurstrokov.resume.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/actuator/health")
public class ActuatorController {
    @Timed(value = "get_actuator_health", histogram = true, percentiles = 0.95)
    @GetMapping
    public ResponseEntity<String> actuator() {
        return new ResponseEntity<>("/actuator/health", HttpStatus.OK);
    }
}
