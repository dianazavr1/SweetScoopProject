package com.example.sweetscoopback.repo;

import com.example.sweetscoopback.entity.ProductBasket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductBasketRepository extends JpaRepository<ProductBasket, Long> {

    List<ProductBasket> findByCartId(Long cartId);
}
