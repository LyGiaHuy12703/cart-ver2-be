package com.example.springboot3_backend_jwt_auth_cart.repository;

import com.example.springboot3_backend_jwt_auth_cart.models.RefreshToken;
import com.example.springboot3_backend_jwt_auth_cart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUser(User user);

    RefreshToken findByToken(String token);
}
