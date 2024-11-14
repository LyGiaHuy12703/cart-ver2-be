package com.example.springboot3_backend_jwt_auth_cart.controllers;

import com.example.springboot3_backend_jwt_auth_cart.core.ResponseSuccess;
import com.example.springboot3_backend_jwt_auth_cart.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseSuccess allProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "4") int size
    ) {
        var result = productService.getAllProducts(page, size);
        return new ResponseSuccess(200,"Get Products Success",result);
    }
}
