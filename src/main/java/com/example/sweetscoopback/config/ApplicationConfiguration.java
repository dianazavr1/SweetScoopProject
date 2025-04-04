package com.example.sweetscoopback.config;
import com.example.sweetscoopback.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration // Аннотация указывает, что этот класс является конфигурационным
public class ApplicationConfiguration {

    private final UserRepository usersRepository;

    /**
     * Внедрение зависимости через конструктор.
     * Spring автоматически передаст UserRepository в этот класс.
     */
    public ApplicationConfiguration(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Создаём бин UserDetailsService.
     * Он отвечает за поиск пользователя в базе данных по email (username).
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> usersRepository.findByEmail(username) // Ищем пользователя по email
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден")); // Если не найден, выбрасываем исключение
    }

    /**
     * Бин для кодирования паролей с помощью BCrypt.
     * BCrypt автоматически добавляет соль, что делает хеширование более безопасным.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Определяем AuthenticationManager.
     * Этот менеджер используется для аутентификации пользователей.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Получаем менеджер аутентификации из конфигурации
    }

    /**
     * Определяем AuthenticationProvider.
     * Он отвечает за проверку учетных данных пользователя.
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // Используем DAO-провайдер

        authProvider.setUserDetailsService(userDetailsService()); // Указываем, как искать пользователя
        authProvider.setPasswordEncoder(passwordEncoder()); // Указываем, как кодировать пароли

        return authProvider;
    }
}

