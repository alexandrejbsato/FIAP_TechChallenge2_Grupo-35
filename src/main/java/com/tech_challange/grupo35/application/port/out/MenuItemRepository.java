package com.tech_challange.grupo35.application.port.out;

import com.tech_challange.grupo35.domain.model.MenuItem;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository {
    MenuItem save(MenuItem menuItem);
    List<MenuItem> findByRestaurantId(UUID restaurantId);
    Optional<MenuItem> findByIdAndRestaurantId(UUID id, UUID restaurantId);
    boolean existsByIdAndRestaurantId(UUID id, UUID restaurantId);
    void deleteById(UUID id);
}
