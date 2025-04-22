package com.example.sweetscoopback.dto;

public class UserResponseDTO {
    private UserDTO user;
    private CartDTO cart;

    public UserResponseDTO(UserDTO user, CartDTO cart) {
        this.user = user;
        this.cart = cart;
    }

    // Геттеры и сеттеры

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }
}

