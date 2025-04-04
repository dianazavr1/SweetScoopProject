package com.example.sweetscoopback.service;
import com.example.sweetscoopback.entity.User;
import com.example.sweetscoopback.repo.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository usersRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(@Valid User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Пароль не может быть null или пустым");
        }

        // Проверяем, есть ли уже пользователь с таким email
        if (usersRepository.existsByEmail(user.getEmail())) {
            return "User with this email already exists.";
        }

        try {
            // Шифруем пароль перед сохранением
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            // Сохраняем пользователя в базе данных
            usersRepository.save(user);
            return "User registered successfully!";
        } catch (Exception e) {
            throw new RuntimeException("Error during registration: " + e.getMessage(), e);
        }
    }
    public List<User> allUsers () {
        List<User> users = new ArrayList<>();

        usersRepository.findAll().forEach(users::add);

        return users;
    }
}