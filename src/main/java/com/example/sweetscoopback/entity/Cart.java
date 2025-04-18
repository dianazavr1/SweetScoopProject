package com.example.sweetscoopback.entity;
import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;


@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true) // связь с пользователем
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>(); // инициализируем, чтобы не было NullPointerException

    // ——— Геттеры и сеттеры ———
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // ——— Удобные методы ———

    public void addProduct(Product product) {
        products.add(product);
        product.setCart(this); // устанавливаем связь
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setCart(null); // убираем связь
    }
}
