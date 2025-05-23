package com.example.sweetscoopback.repo;
import com.example.sweetscoopback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
    User findById(int userId); // Метод для поиска пользователя по ID

}

