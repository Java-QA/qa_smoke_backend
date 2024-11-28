package com.example.wishlist.service;

import com.example.wishlist.dto.WishListDto;
import com.example.wishlist.model.User;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.WishListRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы со списками желаний
 */
@Service
@RequiredArgsConstructor
public class WishListService {
    
    private final WishListRepository wishListRepository;
    private final UserService userService;

    /**
     * Создание нового списка желаний
     * @param wishListDto данные списка желаний
     * @param userId идентификатор владельца
     * @return созданный список желаний
     */
    @Transactional
    public WishList createWishList(WishListDto wishListDto, UUID userId) {
        User owner = userService.getUserById(userId);
        
        WishList wishList = new WishList();
        wishList.setTitle(wishListDto.getTitle());
        wishList.setDescription(wishListDto.getDescription());
        wishList.setOwner(owner);
        
        return wishListRepository.save(wishList);
    }

    /**
     * Получение списка желаний по id
     * @param id идентификатор списка желаний
     * @return список желаний
     */
    public WishList getWishListById(UUID id) {
        return wishListRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Список желаний не найден"));
    }

    /**
     * Получение всех списков желаний пользователя
     * @param userId идентификатор пользователя
     * @return список желаний пользователя
     */
    public List<WishList> getAllWishListsByUserId(UUID userId) {
        return wishListRepository.findAllByOwnerId(userId);
    }

    /**
     * Получение всех списков желаний пользователя
     * @param userId идентификатор пользователя
     * @return список желаний пользователя
     */
    public List<WishList> getWishListsByUserId(UUID userId) {
        return wishListRepository.findByOwnerId(userId);
    }

    /**
     * Обновление списка желаний
     * @param id идентификатор списка желаний
     * @param wishListDto новые данные
     * @param userId идентификатор пользователя
     * @return обновленный список желаний
     */
    @Transactional
    public WishList updateWishList(UUID id, WishListDto wishListDto, UUID userId) {
        WishList wishList = getWishListById(id);
        
        if (!wishList.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("У вас нет прав на редактирование этого списка желаний");
        }
        
        wishList.setTitle(wishListDto.getTitle());
        wishList.setDescription(wishListDto.getDescription());
        
        return wishListRepository.save(wishList);
    }

    /**
     * Удаление списка желаний
     * @param id идентификатор списка желаний
     * @param userId идентификатор пользователя
     */
    @Transactional
    public void deleteWishList(UUID id, UUID userId) {
        WishList wishList = getWishListById(id);
        
        if (!wishList.getOwner().getId().equals(userId)) {
            throw new IllegalArgumentException("У вас нет прав на удаление этого списка желаний");
        }
        
        wishListRepository.delete(wishList);
    }
}
