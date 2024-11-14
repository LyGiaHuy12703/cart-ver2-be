package com.example.springboot3_backend_jwt_auth_cart.services;

import com.example.springboot3_backend_jwt_auth_cart.dto.response.CartProductListResponse;
import com.example.springboot3_backend_jwt_auth_cart.dto.response.ProductResponse;
import com.example.springboot3_backend_jwt_auth_cart.exception.AppException;
import com.example.springboot3_backend_jwt_auth_cart.exception.ErrorEnum;
import com.example.springboot3_backend_jwt_auth_cart.models.*;
import com.example.springboot3_backend_jwt_auth_cart.repository.CartRepository;
import com.example.springboot3_backend_jwt_auth_cart.repository.PostatusRepository;
import com.example.springboot3_backend_jwt_auth_cart.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    PostatusRepository postatusRepository;



    public List<CartProductListResponse> listAllProductsInCart() throws AppException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        Cart cart = cartRepository.findByUserAndPostatus(user,CartStatusEnum.CART_PENDING);
        if (cart == null) {
            return null;
        }
        var cartItems = cart.getCartItems();
        if(cart.getCartItems().isEmpty()) {
            return null;
        }
        List<CartProductListResponse> responses = new ArrayList<>();
        cartItems.forEach(cartItem -> {
            CartProductListResponse response = new CartProductListResponse();
            response.setId(cartItem.getId());
            Product product = cartItem.getProduct();
            ProductResponse productResponse = new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getPhoto(),
                    product.getPrice(),
                    product.getDiscountPercent()
            );
            response.setCartId(cartItem.getCart().getId());
            response.setProduct(productResponse);
            response.setQuantity(cartItem.getQuantity());
            responses.add(response);
        });
        return responses;
    }

    public void checkOutCart() throws AppException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        Cart cart = cartRepository.findByUserAndPostatus(user,CartStatusEnum.CART_PENDING);
        if (cart == null) {
            throw new AppException(ErrorEnum.CART_CHECKOUT_ERROR);
        }
        Postatus postatusCheckout = postatusRepository.findByStatus(CartStatusEnum.CART_CHECKOUT);
        if (postatusCheckout == null) {
            throw new AppException(ErrorEnum.CART_CHECKOUT_ERROR);
        }
        cart.setPostatus(postatusCheckout);
        cartRepository.save(cart);
    }
}
