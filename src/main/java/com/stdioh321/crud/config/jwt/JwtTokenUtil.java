package com.stdioh321.crud.config.jwt;

import com.stdioh321.crud.exception.RestGenericExecption;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component(value = "jwttokenutil")
@Data
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 10 * 60 * 60 * 1000;

    @Value("${jwt.secret}")
    private String secret;

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
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        /* If the expirationDate is null, means that the token should not expire */
        /*if (Objects.isNull(expiration)) return false;*/
        return expiration.before(new Date());
    }

    //gera token para user
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, username, false);
    }

    //Cria o token e define tempo de expiração pra ele
    public String doGenerateToken(Map<String, Object> claims, String subject, boolean removeExpiration) {
        removeExpiration = Objects.isNull(removeExpiration) ? false : removeExpiration;
        var jwtBuilder = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()));
        if (!removeExpiration) jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY));
        return jwtBuilder.signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //valida o token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}