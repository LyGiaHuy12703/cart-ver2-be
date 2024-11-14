package com.example.springboot3_backend_jwt_auth_cart.repository;

import com.example.springboot3_backend_jwt_auth_cart.models.Cart;
import com.example.springboot3_backend_jwt_auth_cart.models.CartItem;
import com.example.springboot3_backend_jwt_auth_cart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("select c from CartItem c left join fetch c.product p where p.id = :productId")
    CartItem findByProductId(Long productId);

    CartItem findByCartAndProductId(Cart cart, Long id);

    List<CartItem> findByProduct(Product product);
}
