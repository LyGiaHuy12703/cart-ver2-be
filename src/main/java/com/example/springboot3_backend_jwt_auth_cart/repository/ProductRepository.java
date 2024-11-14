package com.example.springboot3_backend_jwt_auth_cart.repository;

import com.example.springboot3_backend_jwt_auth_cart.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);


}
