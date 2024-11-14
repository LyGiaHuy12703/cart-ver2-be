package com.example.springboot3_backend_jwt_auth_cart.repository;

import com.example.springboot3_backend_jwt_auth_cart.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u JOIN fetch u.roles r where u.username = :username")
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
