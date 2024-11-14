package com.example.springboot3_backend_jwt_auth_cart.repository;

import com.example.springboot3_backend_jwt_auth_cart.models.CartStatusEnum;
import com.example.springboot3_backend_jwt_auth_cart.models.Postatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostatusRepository extends JpaRepository<Postatus, Long> {

    Postatus findByStatus(CartStatusEnum status);
}
