package com.example.sweetscoopback.entity;
import com.example.sweetscoopback.entity.Product;
import com.example.sweetscoopback.entity.ProductBasket;
import com.example.sweetscoopback.entity.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductBasket> productBaskets = new ArrayList<>();

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

    public List<ProductBasket> getProductBaskets() {
        return productBaskets;
    }

    public void setProductBaskets(List<ProductBasket> productBaskets) {
        this.productBaskets = productBaskets;
    }

    // ——— Методы для управления корзиной ———

    public void addProduct(Product product, int quantity) {
        ProductBasket basket = new ProductBasket();
        basket.setProduct(product);
        basket.setCart(this);
        basket.setQuantity(quantity);
        productBaskets.add(basket);
    }

    public void removeProduct(Product product) {
        productBaskets.removeIf(pb -> pb.getProduct().equals(product));
    }
}
