package com.example.sweetscoopback.controller;

import com.example.sweetscoopback.dto.CartDTO;
import com.example.sweetscoopback.service.CartService;
import com.example.sweetscoopback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {


        @Autowired
        private CartService cartService;

        @GetMapping("/{userId}")
        public ResponseEntity<CartDTO> getCartByUserId(@PathVariable int userId) {
            // Получаем корзину пользователя по userId
            CartDTO cartDTO = cartService.getCartByUserId(userId);

            if (cartDTO != null) {
                return ResponseEntity.ok(cartDTO); // Возвращаем корзину пользователя
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Если корзина не найдена
            }
        }
    }


