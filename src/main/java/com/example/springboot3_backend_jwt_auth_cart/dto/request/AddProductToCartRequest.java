package com.example.springboot3_backend_jwt_auth_cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AddProductToCartRequest {
    Long productId;

    @Min(value = 1,message = "Số lượng từ 1 sản phẩm trở lên")
    Integer quantity;

    public AddProductToCartRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
    public AddProductToCartRequest() {}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
