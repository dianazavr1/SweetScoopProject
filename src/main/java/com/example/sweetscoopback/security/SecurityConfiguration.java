package com.example.sweetscoopback.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем защиту CSRF (нужно при работе с REST API)
                .csrf(csrf -> csrf.disable())

                // Указываем, какие запросы разрешены без авторизации
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем доступ к эндпоинтам, связанным с аутентификацией (регистрация, логин)
                        .requestMatchers("/auth/**").permitAll()

                        // Разрешаем доступ к корзине и продуктам для всех пользователей
                        .requestMatchers("/cart/**", "/products/**").permitAll()

                        // Все остальные запросы требуют авторизации
                        .anyRequest().authenticated()
                )

                // Указываем, что сессии не будут использоваться — всё через JWT
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Устанавливаем провайдер аутентификации
                .authenticationProvider(authenticationProvider)

                // Добавляем наш JWT-фильтр до стандартного фильтра логина/пароля
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Возвращаем построенную цепочку фильтров
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Разрешаем запросы с этого адреса (например, твой frontend)
        configuration.setAllowedOrigins(List.of("http://localhost:8005"));

        // Разрешённые HTTP-методы
        configuration.setAllowedMethods(List.of("GET", "POST"));

        // Разрешённые заголовки, например Authorization для JWT
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        // Применяем конфигурацию для всех путей (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        // Возвращаем объект конфигурации
        return source;
    }
}

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.List;
//
//// Указываем, что это конфигурационный класс Spring
//@Configuration
//
//// Включаем Spring Security в проекте
//@EnableWebSecurity
//
//// Включаем поддержку аннотаций безопасности, например @PreAuthorize
//@EnableMethodSecurity
//public class SecurityConfiguration {
//
//    // Провайдер аутентификации (проверяет имя пользователя и пароль)
//    private final AuthenticationProvider authenticationProvider;
//
//    // JWT-фильтр, который будет перехватывать запросы и проверять токен
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    // Конструктор класса, Spring сам передаст нужные объекты
//    public SecurityConfiguration(
//            JwtAuthenticationFilter jwtAuthenticationFilter,
//            AuthenticationProvider authenticationProvider
//    ) {
//        this.authenticationProvider = authenticationProvider;
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }
//
//    // Основной метод конфигурации безопасности
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // Отключаем защиту CSRF (нужно при работе с REST API)
//                .csrf(csrf -> csrf.disable())
//
//                // Указываем, какие запросы разрешены без авторизации
//                .authorizeHttpRequests(auth -> auth
//                        // Все запросы, начинающиеся с /auth/**, доступны всем (например, регистрация и логин)
//                        .requestMatchers("/auth/**").permitAll()
//
//                        // Все остальные запросы требуют авторизации (нужно быть залогиненым)
//                        .anyRequest().authenticated()
//                )
//
//                // Указываем, что сессии не будут использоваться — всё через JWT
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                // Устанавливаем провайдер аутентификации
//                .authenticationProvider(authenticationProvider)
//
//                // Добавляем наш JWT-фильтр до стандартного фильтра логина/пароля
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        // Возвращаем построенную цепочку фильтров
//        return http.build();
//    }
//
//    // Конфигурация CORS (разрешаем фронтенду обращаться к бэкенду с другого порта)
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        // Разрешаем запросы с этого адреса (например, твой frontend)
//        configuration.setAllowedOrigins(List.of("http://localhost:8005"));
//
//        // Разрешённые HTTP-методы
//        configuration.setAllowedMethods(List.of("GET", "POST"));
//
//        // Разрешённые заголовки, например Authorization для JWT
//        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//
//        // Применяем конфигурацию для всех путей (/**)
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//
//        // Возвращаем объект конфигурации
//        return source;
//    }
//}
//
