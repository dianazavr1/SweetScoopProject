package com.example.sweetscoopback.service;
import com.example.sweetscoopback.entity.Users;
import com.example.sweetscoopback.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(Users user) {
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
}



