package com.stdioh321.crud.controller.auth;

import com.stdioh321.crud.config.jwt.JwtTokenUtil;
import com.stdioh321.crud.exception.RestGenericExecption;
import com.stdioh321.crud.model.AuthRequest;
import com.stdioh321.crud.model.AuthResponse;
import com.stdioh321.crud.service.CustomUserDetailsService;
import com.stdioh321.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest req, HttpServletRequest request) {
        Authentication auth = null;
        User user = null;
        try {
            var tempUser = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
            auth = authenticationManager.authenticate(tempUser);
            user = (User) auth.getPrincipal();


        } catch (Exception e) {
            throw new RestGenericExecption("Incorrect username or password", e, HttpStatus.UNAUTHORIZED, null, null);
        }
        com.stdioh321.crud.model.User tempUser = userService.getByUsernameOrEmail(req.getUsername());
        return ResponseEntity.ok(new AuthResponse(jwtTokenUtil.doGenerateToken(new HashMap<>() {{
            put("user-agent", request.getHeader("User-Agent"));
            put("ip", request.getRemoteAddr());
            put("id", tempUser.getId());
            put("roles", tempUser.getRoleNames());
        }}, user.getUsername(), false)));
    }

    @RequestMapping("/me")
    public ResponseEntity<?> me() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(userService.getById(UUID.fromString(auth.getName())));
    }

}
