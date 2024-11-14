package com.example.springboot3_backend_jwt_auth_cart.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public class ProductResponse {
    Long productId;
    String name;
    String photo;

    BigDecimal price;
    Double discountPercent;

    public ProductResponse() {}

    public ProductResponse(Long productId, String name, String photo, Double price, Double discountPercent) {
        this.productId = productId;
        this.name = name;
        this.photo = photo;
        this.price = new BigDecimal(price);
        this.discountPercent = discountPercent;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = new BigDecimal(price);
    }

    public Double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(Double discountPercent) {
        this.discountPercent = discountPercent;
    }
}
