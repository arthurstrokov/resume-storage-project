package com.gmail.arthurstrokov.resume.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ActuatorController {

    @GetMapping
    public String actuator() {
        return "/actuator/health";
    }
}
