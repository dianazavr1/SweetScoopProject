package com.example.sweetscoopback.service;

import com.example.sweetscoopback.dto.CartDTO;
import com.example.sweetscoopback.entity.Cart;
import com.example.sweetscoopback.entity.ProductBasket;

import com.example.sweetscoopback.entity.User;
import com.example.sweetscoopback.repo.CartRepository;
import com.example.sweetscoopback.repo.ProductBasketRepository;
import com.example.sweetscoopback.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductBasketRepository productBasketRepository;

    // Метод для получения корзины по ID
    public Cart getCartById(Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }

    // Метод для получения всех товаров в корзине через ProductBasket
    public List<ProductBasket> getAllProductBaskets(Long cartId) {
        return productBasketRepository.findByCartId(cartId); // Ищем все записи в ProductBasket для данной корзины
    }
    @Autowired
    private UserRepository userRepository;

    public CartDTO getCartByUserId(int userId) {
        // Находим пользователя по userId
        User user = userRepository.findById(userId);

        if (user != null && user.getCart() != null) {
            // Преобразуем корзину пользователя в CartDTO и возвращаем
            return new CartDTO(user.getCart());
        } else {
            // Если корзина не найдена
            return null;
        }
    }
}
