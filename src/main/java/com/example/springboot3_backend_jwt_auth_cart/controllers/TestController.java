package com.example.springboot3_backend_jwt_auth_cart.controllers;

import com.example.springboot3_backend_jwt_auth_cart.core.ResponseSuccess;
import com.example.springboot3_backend_jwt_auth_cart.models.User;
import com.example.springboot3_backend_jwt_auth_cart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/user")
    public ResponseSuccess getUser() {
        User user = userRepository.findByUsername("KhaiBui").get();
        return new ResponseSuccess(200,"Get User Success",user.getUsername());
    }
}
