package com.example.wishlist.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO для подарка
 */
@Data
@NoArgsConstructor
public class GiftDto {
    private UUID id;

    /**
     * Название подарка
     */
    @NotBlank(message = "Название подарка не может быть пустым")
    @Size(max = 255, message = "Название подарка не может быть длиннее 255 символов")
    private String name;

    /**
     * Описание подарка
     */
    @Size(max = 1000, message = "Описание подарка не может быть длиннее 1000 символов")
    private String description;

    /**
     * URL изображения подарка
     */
    private String imageUrl;

    /**
     * Цена подарка
     */
    @NotNull(message = "Цена подарка должна быть указана")
    @Positive(message = "Цена подарка должна быть положительной")
    private BigDecimal price;
    
    /**
     * Ссылка на магазин
     */
    @Pattern(
        regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$",
        message = "Некорректная ссылка на магазин"
    )
    private String storeUrl;
    
    private boolean reserved;
}
