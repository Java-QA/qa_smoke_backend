package com.example.wishlist.controller;

import com.example.wishlist.dto.WishListDto;
import com.example.wishlist.model.User;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.UserService;
import com.example.wishlist.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Контроллер для работы со списками желаний
 */
@RestController
@RequestMapping("/api/wishlists")
@RequiredArgsConstructor
@Tag(name = "Списки желаний", description = "API для работы со списками желаний")
@SecurityRequirement(name = "Bearer Authentication")
public class WishListController {

    private final WishListService wishListService;
    private final UserService userService;

    /**
     * Создание нового списка желаний
     * @param wishListDto данные списка желаний
     * @param userDetails данные авторизованного пользователя
     * @return созданный список желаний
     */
    @PostMapping
    @Operation(summary = "Создание нового списка желаний")
    public ResponseEntity<WishList> createWishList(
            @Valid @RequestBody WishListDto wishListDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        WishList wishList = wishListService.createWishList(wishListDto, user.getId());
        return ResponseEntity.ok(wishList);
    }

    /**
     * Получение списка желаний по id
     * @param id идентификатор списка желаний
     * @return список желаний
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение списка желаний по id")
    public ResponseEntity<WishList> getWishList(@PathVariable UUID id) {
        WishList wishList = wishListService.getWishListById(id);
        return ResponseEntity.ok(wishList);
    }

    /**
     * Получение всех списков желаний пользователя по его ID
     * @param userId идентификатор пользователя
     * @return список желаний пользователя
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Получение всех списков желаний пользователя по его ID")
    public ResponseEntity<List<WishList>> getUserWishLists(@PathVariable UUID userId) {
        List<WishList> wishLists = wishListService.getAllWishListsByUserId(userId);
        return ResponseEntity.ok(wishLists);
    }

    /**
     * Получение всех списков желаний текущего пользователя
     * @param userDetails данные авторизованного пользователя
     * @return список желаний пользователя
     */
    @GetMapping
    @Operation(summary = "Получение всех списков желаний текущего пользователя")
    public ResponseEntity<List<WishList>> getCurrentUserWishLists(
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        List<WishList> wishLists = wishListService.getAllWishListsByUserId(user.getId());
        return ResponseEntity.ok(wishLists);
    }

    /**
     * Обновление списка желаний
     * @param id идентификатор списка желаний
     * @param wishListDto новые данные
     * @param userDetails данные авторизованного пользователя
     * @return обновленный список желаний
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновление списка желаний")
    public ResponseEntity<WishList> updateWishList(
            @PathVariable UUID id,
            @Valid @RequestBody WishListDto wishListDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        WishList wishList = wishListService.updateWishList(id, wishListDto, user.getId());
        return ResponseEntity.ok(wishList);
    }

    /**
     * Удаление списка желаний
     * @param id идентификатор списка желаний
     * @param userDetails данные авторизованного пользователя
     * @return статус операции
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление списка желаний")
    public ResponseEntity<?> deleteWishList(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        wishListService.deleteWishList(id, user.getId());
        return ResponseEntity.ok().build();
    }
}
