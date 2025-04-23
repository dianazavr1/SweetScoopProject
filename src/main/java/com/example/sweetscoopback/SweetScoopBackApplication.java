package com.example.sweetscoopback;

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

        TotalBasketService basket = new TotalBasketService();

        basket.addAmount(100.50);
        basket.addAmount(50.75);
        basket.printTotal();  // Вывод: Итоговая сумма корзины: 151.25

        basket.clearBasket();
        basket.printTotal();  // Вывод: Итоговая сумма корзины: 0.0
    }

}
