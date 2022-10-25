package com.gmail.arthurstrokov.resume.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Артур Александрович Строков
 * @email astrokov@clevertec.ru
 * @created 18.10.2022
 * @updated 19.10.2022
 */
@Slf4j
@RestController
public class LoadLinkController {

    @GetMapping
    public ResponseEntity<String> links(HttpServletRequest request) {
        log.info("{}/swagger", request.getRequestURL());
        log.info("{}/actuator", request.getRequestURL());
        log.info("{}/actuator/health", request.getRequestURL());
        log.info("{}/actuator/prometheus", request.getRequestURL());
        return new ResponseEntity<>("Hello from resume-storage-project!", HttpStatus.OK);
    }
}
