package com.example.springboot3_backend_jwt_auth_cart.dto.response;

public class ProductFullResponse extends ProductResponse{
    Long stock;

    public ProductFullResponse(Long productId, String name, String photo, Double price, Double discountPercent, Long stock) {
        super(productId, name, photo, price, discountPercent);
        this.stock = stock;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}
