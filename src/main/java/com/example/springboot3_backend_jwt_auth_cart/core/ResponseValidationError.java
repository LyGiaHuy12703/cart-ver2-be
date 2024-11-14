package com.example.springboot3_backend_jwt_auth_cart.core;

public class ResponseValidationError {
    int code;
    String message;
    Object data;
    public ResponseValidationError() {}

    public ResponseValidationError(Object data) {
        this.data = data;
        this.code = 422;
        this.message = "Validation Error";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
