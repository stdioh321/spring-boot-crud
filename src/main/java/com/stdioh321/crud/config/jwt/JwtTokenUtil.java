package com.stdioh321.crud.config.jwt;

import com.stdioh321.crud.exception.RestGenericExecption;
import com.stdioh321.crud.model.User;
import com.stdioh321.crud.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

@Component(value = "jwttokenutil")
@Data
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 60 * 1000;

    public static Set<String> blackList = new HashSet<>();

    @Autowired
    private UserService userService;

    @Value("${jwt.secret}")
    private String secret;

    @Value(value = "${jwt.expiretime}")
    public long jwtExpiretime;

    //retorna o username do token jwt
    public String getUsernameFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);
    }

    //retorna expiration date do token jwt
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);

    }

    //para retornar qualquer informação do token nos iremos precisar da secret key
    public Claims getAllClaimsFromToken(String token) {
        Claims claims = null;
        claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims;
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //gera token para user
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username);
    }

    //Cria o token e define tempo de expiração pra ele
    public String doGenerateToken(Map<String, Object> claims, String subject) {

        Date issued = new Date(System.currentTimeMillis());
        Date expiration = new Date(issued.getTime() + (jwtExpiretime * JWT_TOKEN_VALIDITY));
        var jwtBuilder = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(issued).setExpiration(expiration);

        return jwtBuilder.signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //valida o token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String generateCustomTokenWithUsername(String username, HttpServletRequest request) {
        User tempUser = userService.getByUsernameOrEmail(username);
        HashMap claims = new HashMap();
        claims.put("user-agent", request.getHeader("User-Agent"));
        claims.put("ip", request.getRemoteAddr());
        claims.put("id", tempUser.getId());
        claims.put("roles", tempUser.getRoleNames());

        return doGenerateToken(claims, tempUser.getId().toString());
    }

    public String generateCustomTokenWithId(String id, HttpServletRequest request) {
        User tempUser = userService.getById(id);
        var claims = new HashMap();
        claims.put("user-agent", request.getHeader("User-Agent"));
        claims.put("ip", request.getRemoteAddr());
        claims.put("id", tempUser.getId());
        claims.put("roles", tempUser.getRoleNames());

        return doGenerateToken(claims, tempUser.getId());
    }

}