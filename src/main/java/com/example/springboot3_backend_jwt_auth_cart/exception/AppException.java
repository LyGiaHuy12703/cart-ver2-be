package com.example.springboot3_backend_jwt_auth_cart.exception;

public class AppException extends Exception {
    ErrorEnum errorEnum;
    public AppException(ErrorEnum errorEnum) {
        super(errorEnum.message);
        this.errorEnum = errorEnum;
    }
    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }
}
