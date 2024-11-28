package com.example.wishlist.repository;

import com.example.wishlist.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Репозиторий для работы со списками желаний
 */
@Repository
public interface WishListRepository extends JpaRepository<WishList, UUID> {
    
    /**
     * Поиск всех списков желаний пользователя
     * @param ownerId идентификатор владельца
     * @return список желаний
     */
    List<WishList> findAllByOwnerId(UUID ownerId);
    
    List<WishList> findByOwnerId(UUID ownerId);
}
