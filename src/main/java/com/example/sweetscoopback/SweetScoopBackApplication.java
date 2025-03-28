package com.example.sweetscoopback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.sweetscoopback.entity")
public class SweetScoopBackApplication {
    public static void main(String[] args) {
        SpringApplication.run(SweetScoopBackApplication.class, args);
    }
}