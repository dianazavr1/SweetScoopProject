package com.example.sweetscoopback.service;

public class TotalBasketService {
    private double totalAmount;

    // Конструктор
    public TotalBasketService() {
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

    // Метод для получения итоговой суммы
    public double getTotalAmount() {
        return totalAmount;
    }

    // Метод для очистки корзины
    public void clearBasket() {
        totalAmount = 0.0;
    }

    // Метод для вывода суммы на экран
    public void printTotal() {
        System.out.println("Итоговая сумма корзины: " + totalAmount);
    }
}

