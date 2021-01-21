package com.stdioh321.crud.controller.api;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.url}")
public class Tmp1Controller {

    @GetMapping("/role-admin")
    public ResponseEntity getWithRoleAdmin(){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok("Only WITH role ADMIN ");
    }
    @GetMapping("/role-user")
    public ResponseEntity getWithRoleUser(){
        return ResponseEntity.ok("Only WITH role USER");
    }
}
