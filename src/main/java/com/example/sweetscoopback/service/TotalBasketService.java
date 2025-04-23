package com.example.sweetscoopback.service;

import com.example.sweetscoopback.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class TotalBasketService {
    private List<Product> products;
    private double totalAmount;

    // Конструктор
    public TotalBasketService() {
        this.products = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    // Метод для добавления суммы в корзину
    public void addAmount(double amount) {
        if (amount > 0) {
            this.totalAmount += amount;
        } else {
            System.out.println("Сумма должна быть положительной");
        }
    }

//    // Метод для получения итоговой суммы
//    public double getTotalAmount() {
//        return totalAmount;
//    }

    // Метод для очистки корзины
    public void clearBasket() {
        totalAmount = 0.0;
    }


    // Метод для добавления товара в корзину
    public void addProduct(Product product) {
        products.add(product);
    }

    // Метод для подсчета итоговой стоимости корзины
    public double getTotalAmount() {
        double totalAmount = 0.0;
        for (Product product : products) {
            totalAmount += product.getTotalPrice();
        }
        return totalAmount;
    }

    // Метод для вывода итоговой суммы на экран
    public void printTotal() {
        System.out.println("Итоговая сумма корзины: " + getTotalAmount());
    }
}

