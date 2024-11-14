package com.example.springboot3_backend_jwt_auth_cart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class CreateProductRequest {
    @NotBlank
    String name;

    @PositiveOrZero
    Double price;

    @PositiveOrZero
    Double discountPercent;

    @PositiveOrZero
    Long stock;

    public CreateProductRequest() {
    }

    public CreateProductRequest(String name, Double price, Double discountPercent, Long stock) {
        this.name = name;
        this.price = price;
        this.discountPercent = discountPercent;
        this.stock = stock;
    }

    public @NotBlank String getName() {
        return name;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public @PositiveOrZero Double getPrice() {
        return price;
    }

    public void setPrice(@PositiveOrZero Double price) {
        this.price = price;
    }

    public @PositiveOrZero Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(@PositiveOrZero Double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public @PositiveOrZero Long getStock() {
        return stock;
    }

    public void setStock(@PositiveOrZero Long stock) {
        this.stock = stock;
    }
}
