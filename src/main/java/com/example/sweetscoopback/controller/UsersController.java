package com.example.sweetscoopback.controller;
import com.example.sweetscoopback.entity.Users;
import com.example.sweetscoopback.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody Users user) {
        // Вызов сервиса для регистрации пользователя
        String result = usersService.registerUser(user);

        // Возвращаем результат
        if (result.equals("User registered successfully!")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }
}