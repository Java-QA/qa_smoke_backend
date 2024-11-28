package com.example.wishlist.service;

import com.example.wishlist.dto.GiftDto;
import com.example.wishlist.model.Gift;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.GiftRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с подарками
 */
@Service
@RequiredArgsConstructor
public class GiftService {
    
    private static final Logger log = LoggerFactory.getLogger(GiftService.class);
    
    private final GiftRepository giftRepository;
    private final WishListService wishListService;

    /**
     * Создание нового подарка
     * @param giftDto данные подарка
     * @param wishListId идентификатор списка желаний
     * @param userId идентификатор пользователя
     * @return созданный подарок
     */
    @Transactional
    public Gift createGift(GiftDto giftDto, UUID wishListId, UUID userId) {
        log.debug("Creating gift for wishlist {} by user {}", wishListId, userId);
        
        WishList wishList = wishListService.getWishListById(wishListId);
        if (wishList == null) {
            throw new IllegalArgumentException("WishList not found with id: " + wishListId);
        }
        
        if (!wishList.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("У вас нет прав на добавление подарков в этот список");
        }
        
        Gift gift = new Gift();
        gift.setName(giftDto.getName());
        gift.setDescription(giftDto.getDescription());
        gift.setImageUrl(giftDto.getImageUrl());
        gift.setPrice(giftDto.getPrice());
        gift.setReserved(false);
        
        // Explicitly set the wishList and verify it's set
        gift.setWishList(wishList);
        if (gift.getWishList() == null) {
            throw new IllegalStateException("Failed to set wishList reference");
        }
        
        log.debug("Saving gift with wishList ID: {}", wishList.getId());
        Gift savedGift = giftRepository.save(gift);
        
        // Verify the saved gift has the wishList properly set
        if (savedGift.getWishList() == null) {
            throw new IllegalStateException("WishList reference was lost during save");
        }
        
        log.debug("Successfully created gift {} for wishList {}", savedGift.getId(), savedGift.getWishList().getId());
        return savedGift;
    }

    /**
     * Получение подарка по id
     * @param id идентификатор подарка
     * @return подарок
     */
    public Gift getGiftById(UUID id) {
        return giftRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Подарок не найден"));
    }

    /**
     * Получение всех подарков из списка желаний
     * @param wishListId идентификатор списка желаний
     * @return список подарков
     */
    public List<Gift> getAllGiftsByWishListId(UUID wishListId) {
        return giftRepository.findAllByWishListId(wishListId);
    }

    /**
     * Обновление подарка
     * @param id идентификатор подарка
     * @param giftDto новые данные
     * @param userId идентификатор пользователя
     * @return обновленный подарок
     */
    @Transactional
    public Gift updateGift(UUID id, GiftDto giftDto, UUID userId) {
        Gift gift = getGiftById(id);
        
        if (!gift.getWishList().getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("У вас нет прав на редактирование этого подарка");
        }
        
        gift.setName(giftDto.getName());
        gift.setDescription(giftDto.getDescription());
        gift.setImageUrl(giftDto.getImageUrl());
        gift.setPrice(giftDto.getPrice());
        
        return giftRepository.save(gift);
    }

    /**
     * Удаление подарка
     * @param id идентификатор подарка
     * @param userId идентификатор пользователя
     */
    @Transactional
    public void deleteGift(UUID id, UUID userId) {
        Gift gift = getGiftById(id);
        
        if (!gift.getWishList().getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("У вас нет прав на удаление этого подарка");
        }
        
        giftRepository.delete(gift);
    }

    /**
     * Переключение статуса зарезервированности подарка
     * @param id идентификатор подарка
     * @return подарок
     */
    @Transactional
    public Gift toggleReserved(UUID id) {
        Gift gift = getGiftById(id);
        gift.setReserved(!gift.isReserved());
        return giftRepository.save(gift);
    }

    /**
     * Резервирование подарка
     * @param giftId идентификатор подарка
     * @param userId идентификатор пользователя
     */
    @Transactional
    public void reserveGift(UUID giftId, UUID userId) {
        Gift gift = getGiftById(giftId);
        if (gift.isReserved()) {
            throw new IllegalStateException("Gift is already reserved");
        }
        gift.setReserved(true);
        giftRepository.save(gift);
    }
}
