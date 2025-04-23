package com.example.sweetscoopback.service;

import com.example.sweetscoopback.dto.CartDTO;
import com.example.sweetscoopback.entity.*;

import com.example.sweetscoopback.repo.CartItemRepository;
import com.example.sweetscoopback.repo.CartRepository;
import com.example.sweetscoopback.repo.ProductBasketRepository;
import com.example.sweetscoopback.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductBasketRepository productBasketRepository;
    @Autowired
    private CartItemRepository cartItemRepository;


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


    // Метод для добавления товара в корзину
    public boolean addProductToCart(User user, Product product, int quantity) {
        // Получаем корзину пользователя
        Cart cart = user.getCart();
        if (cart == null) {
            // Если у пользователя нет корзины, создаем новую
            cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        // Проверяем, есть ли уже этот товар в корзине
        Optional<CartItem> existingItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (existingItem.isPresent()) {
            // Если товар уже есть в корзине, увеличиваем его количество
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepository.save(cartItem);
        } else {
            // Если товара нет в корзине, добавляем новый элемент
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }

        return true;
    }
    public boolean removeProductFromCart(User user, Product product) {
        Cart cart = user.getCart();
        if (cart == null) return false;

        // Предположим, у Cart есть список cartItems
        List<CartItem> items = cart.getItems();

        Optional<CartItem> itemToRemove = items.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (itemToRemove.isPresent()) {
            items.remove(itemToRemove.get());
            cartRepository.save(cart); // если cart обновляется через репозиторий
            return true;
        } else {
            return false;
        }
    }



}
