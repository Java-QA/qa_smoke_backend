package com.example.wishlist.repository;

import com.example.wishlist.model.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы с подарками
 */
@Repository
public interface GiftRepository extends JpaRepository<Gift, UUID> {
    
    /**
     * Поиск всех подарков в списке желаний
     * @param wishListId идентификатор списка желаний
     * @return список подарков
     */
    List<Gift> findAllByWishListId(UUID wishListId);
    
    List<Gift> findByWishListId(UUID wishListId);
}
