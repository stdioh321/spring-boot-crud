package com.stdioh321.crud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.url}/hello")
public class HelloController {

    @GetMapping
    public ResponseEntity get(){
        return new ResponseEntity(new String("Hello"), HttpStatus.OK);
    }
}
