package com.example.sweetscoopback.service;


import com.example.sweetscoopback.entity.*;

import com.example.sweetscoopback.repo.CartRepository;
import com.example.sweetscoopback.repo.ProductBasketRepository;
import com.example.sweetscoopback.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private ProductBasketRepository productBasketRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addProductToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        ProductBasket pb = new ProductBasket();
        pb.setCart(cart);
        pb.setProduct(product);
        pb.setQuantity(quantity);

        productBasketRepository.save(pb);
    }
}

