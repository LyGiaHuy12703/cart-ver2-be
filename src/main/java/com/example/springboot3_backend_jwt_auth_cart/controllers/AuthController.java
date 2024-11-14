package com.example.springboot3_backend_jwt_auth_cart.controllers;

import com.example.springboot3_backend_jwt_auth_cart.core.ResponseSuccess;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.RefreshTokenRequest;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.SignInRequest;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.SignUpRequest;
import com.example.springboot3_backend_jwt_auth_cart.exception.AppException;
import com.example.springboot3_backend_jwt_auth_cart.repository.UserRepository;
import com.example.springboot3_backend_jwt_auth_cart.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSuccess signup(
            @Valid @RequestBody SignUpRequest signUpRequest
    ) throws AppException {
        var result = authService.signup(signUpRequest);
        return new ResponseSuccess(201,result,null);
    }

    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess signin(
            @Valid @RequestBody SignInRequest signInRequest
    ) throws AppException {
        var result = authService.signIn(signInRequest);
        return new ResponseSuccess(201,"Sign in success",result);
    }

    @PostMapping("/signout")
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess signout() throws AppException {
        authService.signOut();
        return new ResponseSuccess(200,"Sign out success",null);
    }

    @PostMapping("/refreshToken")
    @ResponseStatus(HttpStatus.OK)
    public ResponseSuccess refreshToken(
            @Valid @RequestBody RefreshTokenRequest refreshTokenRequest
    ) throws AppException {
        var result = authService.refreshTokenService(refreshTokenRequest);
        return new ResponseSuccess(200,"Refresh Token success",result);
    }
}
