package com.example.springboot3_backend_jwt_auth_cart.controllers;

import com.example.springboot3_backend_jwt_auth_cart.core.ResponseSuccess;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.AddProductToCartRequest;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.CreateProductRequest;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.UpdateProductRequest;
import com.example.springboot3_backend_jwt_auth_cart.exception.AppException;
import com.example.springboot3_backend_jwt_auth_cart.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseSuccess createProduct(
            @Valid @ModelAttribute CreateProductRequest request,
            @RequestParam MultipartFile file
            ) throws IOException {
        productService.createProduct(request,file);
        return new ResponseSuccess(201,"Create Product Success",null);
    }

    @GetMapping("/product/{id}")
    public ResponseSuccess getProduct(@PathVariable Long id) throws AppException {
        var result = productService.getProduct(id);
        return new ResponseSuccess(200,"Get Product Success",result);
    }






    @PutMapping("/product/{productId}")
    public ResponseEntity<ResponseSuccess> updateProduct(
            @PathVariable Long productId,
            @ModelAttribute UpdateProductRequest request,  // Receive the form data
            @RequestParam(required = false) MultipartFile file // Optional file input
    ) {
        try {
            // Process the product update with the file, if provided
            var result = productService.updateProduct(productId, request, file);
            return ResponseEntity.ok(new ResponseSuccess(200, "Product updated successfully.", result));
        } catch (ConfigDataResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseSuccess(404, e.getMessage(), null));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseSuccess(500, "Error updating product image.", null));
        }
    }


    // Delete Product Endpoint
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Product deleted successfully.");
        } catch (ConfigDataResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
