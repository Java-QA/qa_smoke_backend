package com.example.wishlist.controller;

import com.example.wishlist.model.User;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.UserService;
import com.example.wishlist.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы с пользователями
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final WishListService wishListService;

    /**
     * Получение списка всех пользователей
     * @return список пользователей
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Получение списков желаний конкретного пользователя
     * @param userId идентификатор пользователя
     * @return список желаний пользователя
     */
    @GetMapping("/{userId}/wishlists")
    public ResponseEntity<List<WishList>> getUserWishlists(@PathVariable UUID userId) {
        return ResponseEntity.ok(wishListService.getAllWishListsByUserId(userId));
    }
}
