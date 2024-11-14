package com.example.springboot3_backend_jwt_auth_cart.exception;

public enum ErrorEnum {
    EMAIL_EXIST(400,"Email is registered"),
    USERNAME_EXIST(400,"Username is registered"),
    UNAUTHORIZED(401,"Unauthorized"),
    TOKEN_EXPIRED(401,"Token expired"),
    USER_NOT_FOUND(404,"User not found"),
    RT_TOKEN_EXPIRED(400,"Refresh token expired"),
    RT_INVALID_TOKEN(400,"Refresh token invalid"),
    PRODUCT_NOT_FOUND(400,"Product not found"),
    PRODUCT_OUT_STOCK(400,"Product out stock"),
    STATUS_NOT_FOUND(400,"Status not found"),
    CART_CHECKOUT_ERROR(400,"Cart checkout error"),
    ;
    final int code;
    final String message;

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
