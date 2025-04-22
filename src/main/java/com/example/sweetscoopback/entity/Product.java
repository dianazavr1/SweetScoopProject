package com.example.sweetscoopback.entity;

import com.example.sweetscoopback.service.UserProductsService;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantity;

    @OneToMany(mappedBy = "product")
    private List<ProductBasket> productBaskets;

//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "cart_id") // Ссылаемся на корзину через внешний ключ
    //  private Cart cart;



    // Конструктор для создания продукта
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product() {

    }



    // Геттер для name
    public String getName() {
        return name;
    }

    // Сеттер для name
    public void setName(String name) {
        this.name = name;
    }

    // Геттер для price
    public double getPrice() {
        return price;
    }

    // Сеттер для price
    public void setPrice(double price) {
        this.price = price;
    }

    // Геттер для quantity
    public int getQuantity() {
        return quantity;
    }

    // Сеттер для quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Геттер для id
    public Long getId() {
        return id;
    }

    // Сеттер для id (не всегда нужно, если id генерируется автоматически)
    public void setId(Long id) {
        this.id = id;
    }
}
