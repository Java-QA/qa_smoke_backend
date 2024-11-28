package com.example.wishlist.controller;

import com.example.wishlist.dto.GiftDto;
import com.example.wishlist.model.Gift;
import com.example.wishlist.model.User;
import com.example.wishlist.service.GiftService;
import com.example.wishlist.service.UserService;
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
 * Контроллер для работы с подарками
 */
@RestController
@RequestMapping("/api/gifts")
@RequiredArgsConstructor
@Tag(name = "Подарки", description = "API для работы с подарками")
@SecurityRequirement(name = "Bearer Authentication")
public class GiftController {

    private final GiftService giftService;
    private final UserService userService;

    /**
     * Создание нового подарка
     * @param wishListId идентификатор списка желаний
     * @param giftDto данные подарка
     * @param userDetails данные авторизованного пользователя
     * @return созданный подарок
     */
    @PostMapping("/wishlist/{wishListId}")
    @Operation(summary = "Создание нового подарка")
    public ResponseEntity<Gift> createGift(
            @PathVariable UUID wishListId,
            @Valid @RequestBody GiftDto giftDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        Gift gift = giftService.createGift(giftDto, wishListId, user.getId());
        return ResponseEntity.ok(gift);
    }

    /**
     * Получение подарка по id
     * @param id идентификатор подарка
     * @return подарок
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение подарка по id")
    public ResponseEntity<Gift> getGift(@PathVariable UUID id) {
        Gift gift = giftService.getGiftById(id);
        return ResponseEntity.ok(gift);
    }

    /**
     * Получение всех подарков в списке желаний
     * @param wishListId идентификатор списка желаний
     * @return список подарков
     */
    @GetMapping("/wishlist/{wishListId}")
    @Operation(summary = "Получение всех подарков в списке желаний")
    public ResponseEntity<List<Gift>> getAllGiftsInWishList(@PathVariable UUID wishListId) {
        List<Gift> gifts = giftService.getAllGiftsByWishListId(wishListId);
        return ResponseEntity.ok(gifts);
    }

    /**
     * Обновление подарка
     * @param id идентификатор подарка
     * @param giftDto новые данные
     * @param userDetails данные авторизованного пользователя
     * @return обновленный подарок
     */
    @PutMapping("/{id}")
    @Operation(summary = "Обновление подарка")
    public ResponseEntity<Gift> updateGift(
            @PathVariable UUID id,
            @Valid @RequestBody GiftDto giftDto,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        Gift gift = giftService.updateGift(id, giftDto, user.getId());
        return ResponseEntity.ok(gift);
    }

    /**
     * Удаление подарка
     * @param id идентификатор подарка
     * @param userDetails данные авторизованного пользователя
     * @return статус операции
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление подарка")
    public ResponseEntity<?> deleteGift(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        giftService.deleteGift(id, user.getId());
        return ResponseEntity.ok().build();
    }

    /**
     * Переключение статуса зарезервированности подарка
     * @param id идентификатор подарка
     * @return подарок
     */
    @PutMapping("/{id}/reserve")
    @Operation(summary = "Переключение статуса зарезервированности подарка")
    public ResponseEntity<Gift> toggleReserved(@PathVariable UUID id) {
        Gift gift = giftService.toggleReserved(id);
        return ResponseEntity.ok(gift);
    }
}
