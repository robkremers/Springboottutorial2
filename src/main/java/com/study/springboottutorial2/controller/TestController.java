package com.study.springboottutorial2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/test")
@Slf4j
public class TestController {

    @GetMapping
    public String getTestResponse() {
        log.info("Just a test");
        return "test";
    }

    @GetMapping(path="/{name}")
    public String getTestResponseWithId(@RequestParam String name) {
        log.info("id = {}", name);
        return String.format("Test with id {}", name);
    }
}
