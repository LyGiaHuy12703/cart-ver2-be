package com.example.springboot3_backend_jwt_auth_cart.dto.request;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {
    @NotBlank
    String refreshToken;

    public  RefreshTokenRequest(){}
    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
}
