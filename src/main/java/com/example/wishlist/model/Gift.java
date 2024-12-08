package com.example.wishlist.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Сущность подарка
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "gifts")
public class Gift {
    
    /**
     * Уникальный идентификатор подарка
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Название подарка
     */
    @Column(nullable = false)
    private String name;

    /**
     * Описание подарка
     */
    @Column(length = 1000)
    private String description;

    /**
     * Ссылка на изображение подарка
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * Ссылка на магазин подарка
     */
    @Column(name = "store_url")
    private String storeUrl;

    /**
     * Цена подарка
     */
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    /**
     * Флаг, указывающий, что подарок уже куплен
     */
    @Column(name = "is_reserved")
    private boolean reserved;

    /**
     * Список желаний, к которому относится подарок
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wish_id", nullable = false)
    private WishList wishList;
}
