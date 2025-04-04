package com.example.sweetscoopback.controller;

import com.example.sweetscoopback.entity.User;
import com.example.sweetscoopback.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;  // Сервис для работы с пользователями

    // Конструктор для внедрения зависимости UserService
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Метод для получения текущего аутентифицированного пользователя
    @GetMapping("/me")  // Обработчик GET запроса по пути "/users/me"
    public ResponseEntity<User> authenticatedUser() {
        // Получаем объект аутентификации из контекста безопасности
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Получаем информацию о текущем пользователе (это объект, который был сохранен в контексте аутентификации)
        User currentUser = (User) authentication.getPrincipal();  // Приводим к типу User

        // Возвращаем текущего пользователя в теле ответа с кодом 200 OK
        return ResponseEntity.ok(currentUser);
    }

    // Метод для получения всех пользователей
    @GetMapping("/")  // Обработчик GET запроса по пути "/users/"
    public ResponseEntity<List<User>> allUsers() {
        // Получаем список всех пользователей из сервиса
        List<User> users = userService.allUsers();  // Вызов метода сервиса для получения пользователей

        // Возвращаем список пользователей в теле ответа с кодом 200 OK
        return ResponseEntity.ok(users);  // Список пользователей передается в ResponseEntity
    }
}
