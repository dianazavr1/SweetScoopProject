package com.example.sweetscoopback.repo;

import com.example.sweetscoopback.entity.ProductBasket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductBasketRepository extends JpaRepository<ProductBasket, Long> {

    List<ProductBasket> findByCartId(Long cartId);
}
