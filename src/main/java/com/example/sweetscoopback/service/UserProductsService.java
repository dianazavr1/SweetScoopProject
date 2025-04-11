package com.example.sweetscoopback.service;

import com.example.sweetscoopback.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class UserProductsService {
    private List<Product> products;

    // Конструктор
    public UserProductsService() {
        this.products = new ArrayList<>();
    }

    // Добавление товара
    public void addProduct(String name, double price, int quantity) {
        products.add(new Product(name, price, quantity));
    }

    // Удаление товара по имени
    public boolean removeProduct(String name) {
        return products.removeIf(product -> product.getName().equals(name));
    }

    // Получение всех товаров
    public List<Product> getProducts() {
        return products;
    }

    // Проверка наличия товара
    public boolean hasProduct(String name) {
        return products.stream().anyMatch(product -> product.getName().equals(name));
    }
}

