package com.example.springboot3_backend_jwt_auth_cart.services;

import com.cloudinary.utils.ObjectUtils;
import com.example.springboot3_backend_jwt_auth_cart.config.CloudinaryConfig;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.CreateProductRequest;
import com.example.springboot3_backend_jwt_auth_cart.dto.request.UpdateProductRequest;
import com.example.springboot3_backend_jwt_auth_cart.dto.response.PaginationResponse;
import com.example.springboot3_backend_jwt_auth_cart.dto.response.ProductFullResponse;
import com.example.springboot3_backend_jwt_auth_cart.exception.AppException;
import com.example.springboot3_backend_jwt_auth_cart.exception.ErrorEnum;
import com.example.springboot3_backend_jwt_auth_cart.models.Cart;
import com.example.springboot3_backend_jwt_auth_cart.models.CartItem;
import com.example.springboot3_backend_jwt_auth_cart.models.Product;
import com.example.springboot3_backend_jwt_auth_cart.models.User;
import com.example.springboot3_backend_jwt_auth_cart.repository.CartItemRepository;
import com.example.springboot3_backend_jwt_auth_cart.repository.CartRepository;
import com.example.springboot3_backend_jwt_auth_cart.repository.ProductRepository;
import com.example.springboot3_backend_jwt_auth_cart.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CloudinaryConfig cloudinaryConfig;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;


    public Object getAllProducts(
            int page,
            int size
    ) {
        var pageRequest = PageRequest.of(page - 1, size);
        var products = productRepository.findAll(pageRequest);
        List<ProductFullResponse> productFullResponses = new ArrayList<>();

        products.forEach(product -> {
            ProductFullResponse productFullResponse = new ProductFullResponse(
                    product.getId(),
                    product.getName(),
                    product.getPhoto(),
                    product.getPrice(),
                    product.getDiscountPercent(),
                    product.getStock()
            );
            productFullResponses.add(productFullResponse);
        });

        PaginationResponse paginationResponse = new PaginationResponse(
                productFullResponses,
                page,
                products.getTotalPages(),
                products.getTotalElements()
        );

        return paginationResponse;
    }

    //Create Product (Admin)
    public void createProduct(
            CreateProductRequest request,
            MultipartFile file
    ) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        //1. upload file
        String url = uploadImg(file,request.getName() + "_" + user.getId());

        //2.Tao Product
        Product product = new Product(
                request.getName(),
                url,
                request.getPrice(),
                request.getDiscountPercent(),
                request.getStock()
        );
        productRepository.save(product);
    }



    //Update Product


    //Delete Product

    //Get By Id

    public ProductFullResponse getProduct(Long id) throws AppException {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new AppException(ErrorEnum.PRODUCT_NOT_FOUND);
        }
        ProductFullResponse productFullResponse = new ProductFullResponse(
                product.getId(),
                product.getName(),
                product.getPhoto(),
                product.getPrice(),
                product.getDiscountPercent(),
                product.getStock()
        );
        return productFullResponse;
    }

    //Update Product
    public Product updateProduct(Long productId, UpdateProductRequest request, MultipartFile file) throws IOException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // Update the product's details
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDiscountPercent(request.getDiscountPercent());
        existingProduct.setStock(request.getStock());

        // If a new file is uploaded, update the image URL
        if (file != null && !file.isEmpty()) {
            String url = uploadImg(file, request.getName() + "_" + productId);
            existingProduct.setPhoto(url);
        }

        return productRepository.save(existingProduct);
    }




    //Delete Product
    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        List<CartItem> cartItem = cartItemRepository.findByProduct(product);
        if(cartItem != null) {
//            cartItemRepository.delete(cartItem);
            throw new RuntimeException("Product existed     in cart of user");
        }
        // Delete the product
        productRepository.delete(product);
    }

    private String uploadImg(MultipartFile file, String unique) throws IOException {
        var result = cloudinaryConfig.cloudinary().uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                        "folder", "product_"+unique
                ));
        return  result.get("secure_url").toString();
    }
}
