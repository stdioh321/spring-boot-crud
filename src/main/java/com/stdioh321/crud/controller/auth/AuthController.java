package com.stdioh321.crud.controller.auth;

import com.stdioh321.crud.config.jwt.JwtTokenUtil;
import com.stdioh321.crud.exception.RestGenericExecption;
import com.stdioh321.crud.model.AuthRequest;
import com.stdioh321.crud.model.AuthResponse;
import com.stdioh321.crud.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest req) {
        Authentication auth;
        try {
            var tempUser = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
            auth = authenticationManager.authenticate(tempUser);

        } catch (Exception e) {
            throw new RestGenericExecption("Incorrect username or password", e, HttpStatus.UNAUTHORIZED, null, null);
        }
        User user = (User) auth.getPrincipal();
        /*final UserDetails userDetails = userDetailsService.loadUserByUsername(req.getUsername());*/
        String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(token));
    }

}
