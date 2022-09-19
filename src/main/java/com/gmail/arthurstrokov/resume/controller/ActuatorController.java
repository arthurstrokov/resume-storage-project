package com.gmail.arthurstrokov.resume.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/actuator/health")
public class ActuatorController {

    @GetMapping
    public String actuator() {
        return "/actuator/health";
    }
}
