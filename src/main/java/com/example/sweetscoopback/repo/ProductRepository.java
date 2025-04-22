package com.example.sweetscoopback.repo;

import com.example.sweetscoopback.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
