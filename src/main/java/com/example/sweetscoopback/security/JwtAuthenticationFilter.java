package com.example.sweetscoopback.security;

import com.example.sweetscoopback.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

// Класс является компонентом Spring, чтобы Spring сам его нашел и зарегистрировал.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Используется для обработки исключений (например, если токен недействителен).
    private final HandlerExceptionResolver handlerExceptionResolver;

    // Сервис для работы с JWT: извлекает данные из токена, проверяет его подлинность.
    private final JwtService jwtService;

    // Сервис, который загружает данные пользователя по email (или username).
    private final UserDetailsService userDetailsService;

    // Конструктор с внедрением зависимостей.
    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    // Метод, который выполняется для каждого HTTP-запроса один раз.
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Получаем заголовок "Authorization" из запроса.
        final String authHeader = request.getHeader("Authorization");

        // Если заголовка нет или он не начинается с "Bearer ", продолжаем цепочку без изменений.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Извлекаем сам JWT из заголовка (удаляем "Bearer ").
            final String jwt = authHeader.substring(7);

            // Извлекаем email пользователя из JWT.
            final String userEmail = jwtService.extractUsername(jwt);

            // Проверяем, есть ли уже аутентификация в контексте безопасности.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Если email найден и пользователь еще не аутентифицирован:
            if (userEmail != null && authentication == null) {
                // Загружаем данные пользователя по email.
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                // Проверяем, действителен ли токен для этого пользователя.
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Создаем объект аутентификации.
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Устанавливаем дополнительные данные, такие как IP и сессия.
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Устанавливаем пользователя как аутентифицированного в контекст Spring Security.
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Продолжаем выполнение цепочки фильтров.
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // Если возникает ошибка (например, токен просрочен), передаём её обработчику исключений.
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}

