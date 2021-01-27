package com.stdioh321.crud.filter;

import com.stdioh321.crud.config.jwt.JwtTokenUtil;
import com.stdioh321.crud.exception.RestGenericExecption;
import com.stdioh321.crud.model.JwtCheckList;
import com.stdioh321.crud.service.CustomUserDetailsService;
import com.stdioh321.crud.service.JwtCheckListService;
import com.stdioh321.crud.utils.Routes;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${api.url}")
    private String urlApi;

    @Autowired
    private JwtCheckListService jwtCheckListService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().equals(urlApi + Routes.AUTHENTICATE) || !request.getRequestURI().startsWith("/api/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("doFilterInternal");


        String jwt = null;
        String username = null;
        try {
            final String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                username = jwtTokenUtil.getUsernameFromToken(jwt);
            } else throw new Exception("Error with header Authorization");


            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                handleSecurityContext(jwt, username, request);
            } else throw new Exception("Invalid Token");

            filterChain.doFilter(request, response);


        } catch (ExpiredJwtException ex) {

            try {

                if (jwtCheckListService.isBlacklisted(jwt)) {
                    throw new Exception("Token is Blacklisted");
                }
                JwtCheckList j = new JwtCheckList();
                j.setToken(jwt);
                j.setBlacklist(true);
                jwtCheckListService.post(j);
                /*JwtTokenUtil.blackList.add(jwt);*/
                String id = ex.getClaims().getSubject();
                String refreshToken = jwtTokenUtil.generateCustomTokenWithId(ex.getClaims().getSubject(), request);
                response.setHeader("Refresh-Token", refreshToken);
                handleSecurityContext(refreshToken, id, request);

                filterChain.doFilter(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getClass());
                var restGenericException = new RestGenericExecption("Invalid Token", ex, HttpStatus.UNAUTHORIZED, request.getServletPath(), null);
                resolver.resolveException(request, response, null, restGenericException);
            }

        } catch (Exception ex) {
            System.out.println(ex.getClass());
            var restGenericException = new RestGenericExecption("Invalid Token", ex, HttpStatus.UNAUTHORIZED, request.getServletPath(), null);
            resolver.resolveException(request, response, null, restGenericException);

        }


    }

    public void handleSecurityContext(String jwt, String username, HttpServletRequest request) throws Exception {
        UserDetails userDetails = userDetailsService.loadUserById(username);
        /*Set<Role> roles = (Set<Role>) userDetails.getAuthorities();*/
        if (jwtTokenUtil.validateToken(jwt, userDetails)) {

            var claims = jwtTokenUtil.getAllClaimsFromToken(jwt);
            if (!claims.get("user-agent").equals(request.getHeader("User-Agent")))
                throw new Exception("Invalid User-Agent");
            if (!claims.get("ip").equals(request.getRemoteAddr())) throw new Exception("Invalid IP");

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
