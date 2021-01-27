package com.stdioh321.crud.controller.api;


import com.stdioh321.crud.utils.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.url}")
public class TestRolesController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(Routes.ROLE_ADMIN)
    public ResponseEntity getWithRoleAdmin() {
        return ResponseEntity.ok("It WORKS. Only WITH role ADMIN");
    }

    @GetMapping(Routes.ROLE_USER)
    public ResponseEntity getWithRoleUser() {
        return ResponseEntity.ok("It WORKS. Only WITH role USER");
    }
}
