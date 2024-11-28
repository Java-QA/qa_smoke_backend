package com.example.wishlist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сущность списка желаний
 */
@Entity
@Table(name = "wishlists")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WishList {
    
    /**
     * Уникальный идентификатор списка желаний
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * Название списка желаний
     */
    @Column(nullable = false)
    private String title;

    /**
     * Описание списка желаний
     */
    @Column
    private String description;

    /**
     * Владелец списка желаний
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"wishLists", "email", "password"})
    private User owner;

    /**
     * Подарки в списке желаний
     */
    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("wishList")
    private List<Gift> gifts = new ArrayList<>();
}
