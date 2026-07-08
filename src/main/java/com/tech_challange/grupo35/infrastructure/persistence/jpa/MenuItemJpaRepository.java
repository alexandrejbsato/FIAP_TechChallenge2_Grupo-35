package com.tech_challange.grupo35.infrastructure.persistence.jpa;

import com.tech_challange.grupo35.infrastructure.persistence.entity.MenuItemEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, UUID> {
    List<MenuItemEntity> findByRestaurantId(UUID restaurantId);
    Optional<MenuItemEntity> findByIdAndRestaurantId(UUID id, UUID restaurantId);
    boolean existsByIdAndRestaurantId(UUID id, UUID restaurantId);
}
