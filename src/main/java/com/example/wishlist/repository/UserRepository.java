package com.example.wishlist.repository;

import com.example.wishlist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с пользователями
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    /**
     * Поиск пользователя по имени пользователя
     * @param username имя пользователя
     * @return пользователь, если найден
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Поиск пользователя по email
     * @param email email пользователя
     * @return пользователь, если найден
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Проверка существования пользователя по имени пользователя
     * @param username имя пользователя
     * @return true, если пользователь существует
     */
    boolean existsByUsername(String username);
    
    /**
     * Проверка существования пользователя по email
     * @param email email пользователя
     * @return true, если пользователь существует
     */
    boolean existsByEmail(String email);
}
