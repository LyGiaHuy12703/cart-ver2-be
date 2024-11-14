package com.example.springboot3_backend_jwt_auth_cart.exception;

public class ExpiredTokenException extends Exception{
    ErrorEnum errorEnum;
    public ExpiredTokenException(ErrorEnum errorEnum) {
        super(errorEnum.message);
        this.errorEnum = errorEnum;
    }
    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }
}
