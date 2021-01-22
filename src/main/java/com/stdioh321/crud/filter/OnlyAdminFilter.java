package com.stdioh321.crud.filter;

import com.stdioh321.crud.exception.RestGenericExecption;
import com.stdioh321.crud.model.Role;
import com.stdioh321.crud.utils.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Component
public class OnlyAdminFilter extends OncePerRequestFilter {

    @Value("${api.url}")
    private String apiUrl;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("OnlyAdminFilter");
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Collection<Role> roles = (Collection<Role>) auth.getAuthorities();
            boolean hasRoleAdmin = roles.stream().anyMatch(role -> {
                return role.getName().equals("ADMIN");
            });
            if (!hasRoleAdmin) {
                throw new Exception("Do not contain the specific ROLE");
            }

            chain.doFilter(request, response);
        } catch (Exception ex) {
            RestGenericExecption restEx = new RestGenericExecption("Forbidden", ex, HttpStatus.FORBIDDEN, null, null);
            resolver.resolveException(request, response, null, restEx);
        }

    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String url = apiUrl + Routes.ROLE_ADMIN;
        String path = request.getRequestURI();

        return !path.startsWith(url);
    }
}
