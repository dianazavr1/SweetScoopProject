package com.example.sweetscoopback.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.firewall.HttpFirewall;
//import org.springframework.security.web.firewall.StrictHttpFirewall;
//import org.springframework.security.web.firewall.RequestRejectedHandler;
//import org.springframework.web.util.HtmlUtils;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(cors -> cors.disable())  // Отключаем CORS (или настроить правильно)
//                .csrf(csrf -> csrf.disable())  // Отключаем CSRF
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/users/register", "/error").permitAll() // Разрешаем доступ к этим маршрутам
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public HttpFirewall relaxedHttpFirewall() {
//        StrictHttpFirewall firewall = new StrictHttpFirewall();
//        firewall.setUnsafeAllowAnyHttpMethod(true); // Разрешаем любые HTTP-методы
//        firewall.setAllowUrlEncodedSlash(true); // Разрешаем закодированные слэши
//        firewall.setAllowUrlEncodedPercent(true); // Разрешаем закодированные проценты (%)
//        return firewall;
//    }
//
//    @Bean
//    public RequestRejectedHandler requestRejectedHandler() {
//        return (request, response, exception) -> {
//            response.sendError(400, "Недопустимый запрос: " + HtmlUtils.htmlEscape(exception.getMessage()));
//        };
//    }
//}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())  // Отключаем CORS
                .csrf(csrf -> csrf.disable())  // Отключаем CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/error").permitAll() // Разрешаем доступ
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
