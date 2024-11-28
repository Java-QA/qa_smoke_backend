package com.example.wishlist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO для списка желаний
 */
@Data
public class WishListDto {
    
    /**
     * Название списка желаний
     */
    @NotBlank(message = "Название списка обязательно")
    private String title;
    
    /**
     * Описание списка желаний
     */
    private String description;
}
