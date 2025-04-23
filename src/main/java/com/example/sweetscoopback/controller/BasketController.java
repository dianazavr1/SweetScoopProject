package com.example.sweetscoopback.controller;

import com.example.sweetscoopback.entity.Product;
import com.example.sweetscoopback.service.TotalBasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/basket")
@CrossOrigin(origins = "*")
public class BasketController {

    private final TotalBasketService basketService;

    @Autowired
    public BasketController(TotalBasketService basketService) {
        this.basketService = basketService;
    }

    // Добавить товар в корзину
    @PostMapping("/add")
    public String addProduct(@RequestBody Product product) {
        basketService.addProduct(product);
        return "Товар добавлен в корзину";
    }

    // Получить все товары из корзины
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return basketService.getAllProducts();
    }

    // Получить итоговую сумму
    @GetMapping("/total")
    public double getTotalAmount() {
        return basketService.getTotalAmount();
    }

    // Очистить корзину
    @PostMapping("/clear")
    public String clearBasket() {
        basketService.clearBasket();
        return "Корзина очищена";
    }
}
