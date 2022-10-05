package com.admin.firstproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/saludo")
public class FirstProjectApplicationRESTController {

    @GetMapping
    public String saludo(){
        return "Hola";
    }
}
