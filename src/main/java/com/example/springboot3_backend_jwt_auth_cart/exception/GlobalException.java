package com.example.springboot3_backend_jwt_auth_cart.exception;

import com.example.springboot3_backend_jwt_auth_cart.core.ResponseError;
import com.example.springboot3_backend_jwt_auth_cart.core.ResponseValidationError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> exceptionHandler(Exception e) {
        String message = e.getMessage();
        return ResponseEntity.status(500).body(new ResponseError(500, message));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseError> badCredentialsHandler(Exception e) {
        ErrorEnum errorEnum = ErrorEnum.UNAUTHORIZED;
        return ResponseEntity.status(errorEnum.code).body(new ResponseError(errorEnum.code, errorEnum.message));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseError> appExceptionHandler(AppException e) {
        ErrorEnum errorEnum = e.getErrorEnum();
        return ResponseEntity.status(errorEnum.code).body(new ResponseError(errorEnum.code, errorEnum.message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseValidationError> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<Map<String,String>> errors = e.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            Map<String, String> map = new HashMap<>();
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
            return map;
        }).toList();
        return ResponseEntity.status(500).body(new ResponseValidationError(errors));
    }
}
