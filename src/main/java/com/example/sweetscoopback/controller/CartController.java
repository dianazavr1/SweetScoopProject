package com.example.sweetscoopback.controller;

import com.example.sweetscoopback.dto.AddProductToCartRequest;
import com.example.sweetscoopback.dto.CartDTO;
import com.example.sweetscoopback.dto.UpdateCartQuantityRequest;
import com.example.sweetscoopback.entity.Product;
import com.example.sweetscoopback.entity.User;
import com.example.sweetscoopback.service.CartService;
import com.example.sweetscoopback.service.UserProductsService;
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
        @Autowired
        private UserService userService;
        @Autowired
        private UserProductsService userProductsService;


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
    // Метод для добавления товара в корзину
    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addProductToCart(@PathVariable Long userId, @RequestBody AddProductToCartRequest request) {
        // Получаем пользователя по userId
        User user = userService.getEntityUserById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }


        // Получаем товар по productId
        Product product = userProductsService.getProductById(request.getProductId());
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        // Добавляем товар в корзину пользователя
        boolean success = cartService.addProductToCart(user, product, request.getQuantity());
        if (success) {
            return ResponseEntity.ok("Product added to cart");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding product to cart");
        }
    }

    // Обрабатывает HTTP DELETE запрос по маршруту /cart/{userId}/remove/{productId}
// Используется для удаления товара из корзины конкретного пользователя
    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<String> removeProductFromCart(
            @PathVariable Long userId,         // ID пользователя, переданный в URL
            @PathVariable Long productId) {    // ID продукта, который нужно удалить

        // Получаем пользователя по ID
        User user = userService.getEntityUserById(userId);
        if (user == null) {
            // Если пользователь не найден, возвращаем статус 404 (Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Получаем продукт по ID
        Product product = userProductsService.getProductById(productId);
        if (product == null) {
            // Если продукт не найден, возвращаем статус 404 (Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        // Пытаемся удалить товар из корзины пользователя
        boolean removed = cartService.removeProductFromCart(user, product);
        if (removed) {
            // Если удаление прошло успешно, возвращаем 200 OK
            return ResponseEntity.ok("Product removed from cart");
        } else {
            // Если что-то пошло не так — возвращаем статус 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove product");
        }
    }
    @PutMapping("/{userId}/update")
    public ResponseEntity<String> updateProductQuantity(
            @PathVariable Long userId,
            @RequestBody UpdateCartQuantityRequest request) {

        boolean updated = cartService.updateProductQuantity(userId, request.getProductId(), request.getQuantity());

        if (updated) {
            return ResponseEntity.ok("Quantity updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found in cart");
        }
    }

}


