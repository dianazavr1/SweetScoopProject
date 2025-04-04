package com.example.sweetscoopback.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service // Аннотация указывает, что этот класс является сервисом Spring и может быть внедрен в другие компоненты
public class JwtService {

    @Value("${security.jwt.secret-key}") // Считываем секретный ключ из application.properties
    private String secretKey;

    @Value("${security.jwt.expiration-time}") // Считываем время жизни токена из application.properties
    private long jwtExpiration;

    /**
     * Извлекает имя пользователя (subject) из токена
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлекает конкретное поле (claim) из токена
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Получаем все claims (данные) из токена
        return claimsResolver.apply(claims); // Применяем переданную функцию для извлечения нужного поля
    }

    /**
     * Генерирует токен без дополнительных данных
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails); // Вызывает перегруженный метод с пустыми доп. данными
    }

    /**
     * Генерирует токен с дополнительными данными
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration); // Создает JWT с заданным временем жизни
    }

    /**
     * Возвращает время жизни токена
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Строит токен с заданными параметрами
     */
    private String buildToken(
            Map<String, Object> extraClaims, // Дополнительные данные для токена
            UserDetails userDetails, // Данные пользователя
            long expiration // Время жизни токена
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims) // Устанавливаем дополнительные данные (claims)
                .setSubject(userDetails.getUsername()) // Устанавливаем subject (имя пользователя)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Устанавливаем дату выпуска токена
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Устанавливаем дату истечения
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Подписываем токен ключом
                .compact(); // Формируем окончательный JWT
    }

    /**
     * Проверяет, действителен ли токен для пользователя
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Извлекаем имя пользователя из токена
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token); // Проверяем совпадение и срок действия
    }

    /**
     * Проверяет, истек ли срок действия токена
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Сравниваем дату истечения с текущей датой
    }

    /**
     * Извлекает дату истечения токена
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Используем extractClaim() для получения даты истечения
    }

    /**
     * Извлекает все claims (данные) из токена
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder() // Создаем билдер парсера JWT
                .setSigningKey(getSignInKey()) // Устанавливаем ключ для подписи
                .build()
                .parseClaimsJws(token) // Разбираем токен
                .getBody(); // Получаем claims
    }

    /**
     * Получает ключ подписи токена из секретного ключа
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Декодируем секретный ключ из Base64
        return Keys.hmacShaKeyFor(keyBytes); // Создаем ключ для алгоритма HMAC-SHA256
    }
}
