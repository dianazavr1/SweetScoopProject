package com.example.sweetscoopback.repo;

import com.example.sweetscoopback.entity.Cart;
import com.example.sweetscoopback.entity.CartItem;
import com.example.sweetscoopback.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
    CartItem findByCartIdAndProductId(Long cartId, Long productId);
   // CartItem findByCartAndProduct(Cart cart, Product product);


}
