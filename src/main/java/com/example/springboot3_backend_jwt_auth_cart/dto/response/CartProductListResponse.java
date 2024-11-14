package com.example.springboot3_backend_jwt_auth_cart.dto.response;

public class CartProductListResponse {
    Long id;
    ProductResponse product;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    Integer quantity;
    Long cartId;

    public CartProductListResponse(ProductResponse product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartProductListResponse() {
    }

    public ProductResponse getProduct() {
        return product;
    }

    public void setProduct(ProductResponse product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
