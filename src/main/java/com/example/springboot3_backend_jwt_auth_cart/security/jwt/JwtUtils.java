package com.example.springboot3_backend_jwt_auth_cart.security.jwt;

import com.example.springboot3_backend_jwt_auth_cart.exception.ErrorEnum;
import com.example.springboot3_backend_jwt_auth_cart.exception.ExpiredTokenException;
import com.example.springboot3_backend_jwt_auth_cart.models.Role;
import com.example.springboot3_backend_jwt_auth_cart.models.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.logging.Logger;

@Component
public class JwtUtils {



    public String generateJwtToken(User user,Long jwtExpire,String jwtSecret) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for(Role role: user.getRoles()) {
            stringJoiner.add(role.getName().name());
        }
        return Jwts.builder()
                .setSubject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(Instant.now().plus(jwtExpire, ChronoUnit.DAYS).toEpochMilli()))
                .claim("scope",stringJoiner.toString())
                .claim("user_id",user.getId())
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
    public String generateJwtToken(User user,Date expire,String jwtSecret) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        for(Role role: user.getRoles()) {
            stringJoiner.add(role.getName().name());
        }
        return Jwts.builder()
                .setSubject(user.getUsername())
                .issuedAt(new Date())
                .expiration(expire)
                .claim("scope",stringJoiner.toString())
                .claim("user_id",user.getId())
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token,String jwtSecret) {
        return Jwts.parser().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String token, String jwtSecret) throws ExpiredTokenException {
        try{
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e) {
            throw new ExpiredTokenException(ErrorEnum.TOKEN_EXPIRED);
           // return false;
        }catch (Exception e) {
            System.out.println("ABC " + e.getMessage() + " " + e.getClass());
            return false;
        }
    }
}
