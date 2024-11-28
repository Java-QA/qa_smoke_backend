package com.example.wishlist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сущность пользователя системы
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    
    /**
     * Уникальный идентификатор пользователя
     */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    /**
     * Имя пользователя (логин)
     */
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    /**
     * Электронная почта пользователя
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Хэшированный пароль пользователя
     */
    @JsonIgnore
    @Column(nullable = false)
    private String password;

    /**
     * Списки желаний пользователя
     */
    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<WishList> wishLists = new ArrayList<>();
}
