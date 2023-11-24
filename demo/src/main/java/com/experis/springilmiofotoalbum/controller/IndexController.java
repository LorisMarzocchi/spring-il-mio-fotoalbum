package com.experis.springilmiofotoalbum.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {
    @GetMapping
    public String home() {
        return "redirect:/photos";
    }
}
