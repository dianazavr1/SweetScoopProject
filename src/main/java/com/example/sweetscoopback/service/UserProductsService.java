package com.example.sweetscoopback.service;

import com.example.sweetscoopback.entity.Cart;
import com.example.sweetscoopback.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class UserProductsService {
    private List<Product> products;

    // Конструктор
    public UserProductsService() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Cart cart, String name, double price, int quantity) {
        Product newProduct = new Product(name, price, quantity);
        newProduct.setCart(cart); // теперь тип совпадает
        cart.getProducts().add(newProduct); // добавляем в корзину
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

