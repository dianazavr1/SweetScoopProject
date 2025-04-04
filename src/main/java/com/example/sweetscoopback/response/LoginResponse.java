package com.example.sweetscoopback.response;

public class LoginResponse {
    private String token;
    private long expiresIn;

    // Конструктор без параметров (для использования в сериализации)
    public LoginResponse() {
    }

    // Конструктор с параметрами
    public LoginResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    // Геттер для token
    public String getToken() {
        return token;
    }

    // Сеттер для token
    public LoginResponse setToken(String token) {
        this.token = token;
        return this; // Возвращаем сам объект, чтобы можно было вызывать chain
    }

    // Геттер для expiresIn
    public long getExpiresIn() {
        return expiresIn;
    }

    // Сеттер для expiresIn
    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this; // Возвращаем сам объект, чтобы можно было вызывать chain
    }
}
