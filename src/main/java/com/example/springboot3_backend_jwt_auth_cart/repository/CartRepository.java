package com.example.springboot3_backend_jwt_auth_cart.repository;

import com.example.springboot3_backend_jwt_auth_cart.models.Cart;
import com.example.springboot3_backend_jwt_auth_cart.models.CartStatusEnum;
import com.example.springboot3_backend_jwt_auth_cart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c left JOIN fetch c.postatus p where c.user = :user and p.status = :postatus")
    Cart findByUserAndPostatus(User user, CartStatusEnum postatus);
}
