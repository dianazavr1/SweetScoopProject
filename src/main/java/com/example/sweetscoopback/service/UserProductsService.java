package com.example.sweetscoopback.service;

import com.example.sweetscoopback.entity.Cart;
import com.example.sweetscoopback.entity.Product;
import com.example.sweetscoopback.entity.ProductBasket;
import com.example.sweetscoopback.repo.ProductBasketRepository;


public class UserProductsService {

    private ProductBasketRepository productBasketRepository;

    public void addProductToCart(Cart cart, Product product, int quantity) {
        ProductBasket productBasket = new ProductBasket();
        productBasket.setCart(cart);
        productBasket.setProduct(product);
        productBasket.setQuantity(quantity);

        // Сохраняем связь в базе данных
        productBasketRepository.save(productBasket);
    }


}

