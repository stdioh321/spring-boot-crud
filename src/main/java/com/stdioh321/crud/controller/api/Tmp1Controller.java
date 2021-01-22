package com.stdioh321.crud.controller.api;


import com.stdioh321.crud.utils.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("${api.url}")
public class Tmp1Controller {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(Routes.ROLE_ADMIN)
    public ResponseEntity getWithRoleAdmin() {
        return ResponseEntity.ok("Only WITH role ADMIN");
    }

    @GetMapping(Routes.ROLE_USER)
    public ResponseEntity getWithRoleUser() {
        return ResponseEntity.ok("Only WITH role USER");
    }
}
