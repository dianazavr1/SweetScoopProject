package com.example.sweetscoopback.service;

import com.example.sweetscoopback.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TotalBasketService {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public double getTotalAmount() {
        return products.stream()
                .mapToDouble(Product::getTotalPrice)
                .sum();
    }

    public void clearBasket() {
        products.clear();
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public void printTotal() {
    }
}
