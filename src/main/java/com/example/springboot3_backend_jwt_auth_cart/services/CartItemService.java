package com.example.springboot3_backend_jwt_auth_cart.services;

import com.example.springboot3_backend_jwt_auth_cart.dto.request.AddProductToCartRequest;
import com.example.springboot3_backend_jwt_auth_cart.exception.AppException;
import com.example.springboot3_backend_jwt_auth_cart.exception.ErrorEnum;
import com.example.springboot3_backend_jwt_auth_cart.models.*;
import com.example.springboot3_backend_jwt_auth_cart.repository.CartItemRepository;
import com.example.springboot3_backend_jwt_auth_cart.repository.CartRepository;
import com.example.springboot3_backend_jwt_auth_cart.repository.PostatusRepository;
import com.example.springboot3_backend_jwt_auth_cart.repository.ProductRepository;
import com.example.springboot3_backend_jwt_auth_cart.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    PostatusRepository postatusRepository;

    @Autowired
    ProductRepository productRepository;



    public void addProductToCart(AddProductToCartRequest request) throws AppException {
        //1. Tim san pham
        Product product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            throw new AppException(ErrorEnum.PRODUCT_NOT_FOUND);
        }
        if(product.getStock() == 0) {
            throw new AppException(ErrorEnum.PRODUCT_OUT_STOCK);
        }
        //2. Lay thong tin user
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        //3. Tim co cart status Pending ko
        Cart cart = cartRepository.findByUserAndPostatus(user, CartStatusEnum.CART_PENDING);
        if (cart == null) {
            //Khong co tao cart
            Postatus statusPending = postatusRepository.findByStatus(CartStatusEnum.CART_PENDING);
            Cart newCart = new Cart(new Date(),user);
            newCart.addStatus(statusPending);
            cart = cartRepository.save(newCart);
        }
        //4. Tim coi san pham co trong cart_item chua
        CartItem foundCartItemProduct = cartItemRepository.findByCartAndProductId(cart, request.getProductId());
        if (foundCartItemProduct == null) {
            CartItem cartItem = new CartItem(request.getQuantity());
            product.addCartItem(cartItem);
            cart.addCartItem(cartItem);
            productRepository.save(product);
            cartRepository.save(cart);
        }else {
            foundCartItemProduct.setQuantity(foundCartItemProduct.getQuantity() + request.getQuantity());
            cartItemRepository.save(foundCartItemProduct);
        }


    }

    public void deleteProductFromCart(Long cartItemId) throws AppException {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);
        if (cartItem == null) {
            throw new AppException(ErrorEnum.PRODUCT_NOT_FOUND);
        }
        cartItemRepository.delete(cartItem);
    }
}
