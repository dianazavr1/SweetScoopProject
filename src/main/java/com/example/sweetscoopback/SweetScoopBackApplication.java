package com.example.sweetscoopback;

import com.example.sweetscoopback.entity.Product;
import com.example.sweetscoopback.service.TotalBasketService;
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

        // Создаем товары
        Product product1 = new Product("Телефон", 500.0, 2); // 2 телефона по 500.0
        Product product2 = new Product("Ноутбук", 1200.0, 1); // 1 ноутбук за 1200.0
        Product product3 = new Product("Наушники", 150.0, 3); // 3 наушника по 150.0

        // Создаем корзину и добавляем товары
        TotalBasketService basket = new TotalBasketService();
        basket.addProduct(product1);
        basket.addProduct(product2);
        basket.addProduct(product3);

        // Выводим итоговую сумму корзины
        basket.printTotal();  // Итоговая сумма корзины: 3300.0
    }

}
