package com.itbcafrica.jwtapplication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @PreAuthorize("hasRole('ADMIN')")// @Preauthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from HomeController";
    }
}
