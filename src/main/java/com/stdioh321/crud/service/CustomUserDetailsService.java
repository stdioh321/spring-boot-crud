package com.stdioh321.crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var tempUser = userService.getByUsernameOrEmail(username);
        if (tempUser == null) throw new BadCredentialsException("Invalid Credentials");
        return new User(tempUser.getId().toString(), tempUser.getPassword(), tempUser.getRoles());
    }

    public UserDetails loadUserById(String id) {
        var tempUser = userService.getById(id);
        if (tempUser == null) throw new BadCredentialsException("Invalid Credentials");
        return new User(tempUser.getId().toString(), tempUser.getPassword(), tempUser.getRoles());
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        var bc = new BCryptPasswordEncoder();
        return bc;

    }


}
