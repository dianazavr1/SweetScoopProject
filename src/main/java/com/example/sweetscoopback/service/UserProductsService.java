package com.example.sweetscoopback.service;

import com.example.sweetscoopback.entity.Cart;
import com.example.sweetscoopback.entity.Product;
import com.example.sweetscoopback.entity.ProductBasket;
import com.example.sweetscoopback.repo.ProductBasketRepository;
import com.example.sweetscoopback.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProductsService {
    @Autowired

    private ProductBasketRepository productBasketRepository;
    @Autowired
    private ProductRepository productRepository;

    public void addProductToCart(Cart cart, Product product, int quantity) {
        ProductBasket productBasket = new ProductBasket();
        productBasket.setCart(cart);
        productBasket.setProduct(product);
        productBasket.setQuantity(quantity);

        // Сохраняем связь в базе данных
        productBasketRepository.save(productBasket);
    }
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}

