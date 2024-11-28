package com.example.wishlist.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO для подарка
 */
@Data
@NoArgsConstructor
public class GiftDto {
    
    /**
     * Название подарка
     */
    @NotBlank(message = "Название подарка не может быть пустым")
    @Size(min = 1, max = 100, message = "Название подарка должно быть между 1 и 100 символами")
    private String name;
    
    /**
     * Описание подарка
     */
    @Size(max = 1000, message = "Описание подарка не может быть длиннее 1000 символов")
    private String description;
    
    /**
     * Изображение подарка
     */
    private String imageUrl;

    /**
     * Цена подарка
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "Цена не может быть отрицательной")
    private BigDecimal price;
}
