package com.example.springboot3_backend_jwt_auth_cart.security.jwt;

import com.example.springboot3_backend_jwt_auth_cart.core.ResponseError;
import com.example.springboot3_backend_jwt_auth_cart.exception.ErrorEnum;
import com.example.springboot3_backend_jwt_auth_cart.exception.ExpiredTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorEnum errorEnum = ErrorEnum.UNAUTHORIZED;

//        if(authException.getCause() instanceof ExpiredTokenException) {
//            System.out.println(authException.getCause().getMessage() + " Het Han Token");
//        }

        //set response status và contentType
        response.setStatus(errorEnum.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        System.out.println(authException.getClass() + " " + authException.getMessage() + " " + authException.getCause() + " AINISN");

        ResponseError responseError = new ResponseError(errorEnum.getCode(), errorEnum.getMessage());

        ObjectMapper mapper = new ObjectMapper();

        response.getWriter().write(mapper.writeValueAsString(responseError));

        response.flushBuffer();
    }
}
