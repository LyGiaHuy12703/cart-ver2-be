package com.example.springboot3_backend_jwt_auth_cart.controllers;

import com.example.springboot3_backend_jwt_auth_cart.core.ResponseSuccess;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.AddProductToCartRequest;
import com.example.springboot3_backend_jwt_auth_cart.exception.AppException;
import com.example.springboot3_backend_jwt_auth_cart.services.CartItemService;
import com.example.springboot3_backend_jwt_auth_cart.services.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    CartItemService cartItemService;

    @Autowired
    CartService cartService;

    @PostMapping("/product")
    public ResponseSuccess addProductCart(
            @Valid @RequestBody AddProductToCartRequest request
    ) throws AppException {
        cartItemService.addProductToCart(request);
        return new ResponseSuccess<>(200,"Add Product To Cart Successfully",null);
    }

    @GetMapping("/products")
    public ResponseSuccess getAllProducts() throws AppException {
        var result = cartService.listAllProductsInCart();
        if(result == null) {
            return new ResponseSuccess<>(200,"Cart is empty",null);
        }else {
            return new ResponseSuccess<>(200,"Get All Product In Cart Successfully",result);
        }
    }

    @DeleteMapping("/item/{id}")
    public ResponseSuccess deleteProductCart(
            @PathVariable Long id
    ) throws AppException {
        cartItemService.deleteProductFromCart(id);
        return new ResponseSuccess<>(200,"Delete Product From Cart Successfully",null);
    }

    @PostMapping("/checkout")
    public ResponseSuccess checkoutCart() throws AppException {
        cartService.checkOutCart();
        return new ResponseSuccess<>(200,"Checkout Successfully",null);
    }
}
