package com.example.springboot3_backend_jwt_auth_cart.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "postatus")
public class Postatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    CartStatusEnum status;
    String description;

    @OneToMany(mappedBy = "postatus",cascade = CascadeType.ALL,orphanRemoval = true)
    Set<Cart> carts = new HashSet<>();

    public Postatus() {}
    public Postatus(String description, CartStatusEnum status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CartStatusEnum status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }
}
