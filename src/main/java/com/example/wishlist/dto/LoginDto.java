package com.example.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO для авторизации пользователя
 */
@Data
public class LoginDto {
    
    /**
     * Имя пользователя
     */
    @NotBlank(message = "Имя пользователя обязательно")
    private String username;
    
    /**
     * Пароль пользователя
     */
    @NotBlank(message = "Пароль обязателен")
    private String password;
}
