package com.example.sweetscoopback;

import com.example.sweetscoopback.service.UserProductsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.sweetscoopback.entity")
public class SweetScoopBackApplication {
    public static void main(String[] args) {
        // Запуск Spring Boot приложения
        SpringApplication.run(SweetScoopBackApplication.class, args);

        // Пример использования сервиса
        UserProductsService userProductsService = new UserProductsService();

        // Добавление товаров
        userProductsService.addProduct("Товар 1", 100.5, 10);
        userProductsService.addProduct("Товар 2", 200.0, 5);

        // Печать всех товаров
        System.out.println("Товары пользователя: " + userProductsService.getProducts());

        // Удаление товара
        userProductsService.removeProduct("Товар 1");

        // Печать всех товаров после удаления
        System.out.println("Товары пользователя после удаления: " + userProductsService.getProducts());
    }
}
