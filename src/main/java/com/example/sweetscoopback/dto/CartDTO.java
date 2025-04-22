package com.example.sweetscoopback.dto;
import com.example.sweetscoopback.entity.Cart;

import java.util.List;
import java.util.stream.Collectors;

public class CartDTO {
    private Long id;
    private Long userId;
    private List<CartItemDTO> items;

    // Конструкторы, геттеры и сеттеры

    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.userId = cart.getUser().getId();
        this.items = cart.getItems().stream()
                .map(item -> new CartItemDTO(item))
                .collect(Collectors.toList());
    }

    public CartDTO(Long id, Long userId, List<CartItemDTO> items) {
        this.id = id;
        this.userId = userId;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
}
