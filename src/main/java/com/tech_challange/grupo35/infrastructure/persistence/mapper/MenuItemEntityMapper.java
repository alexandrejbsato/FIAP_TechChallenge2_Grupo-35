package com.tech_challange.grupo35.infrastructure.persistence.mapper;

import com.tech_challange.grupo35.domain.model.MenuItem;
import com.tech_challange.grupo35.infrastructure.persistence.entity.MenuItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MenuItemEntityMapper {

    private final RestaurantEntityMapper restaurantMapper;

    public MenuItem toDomain(MenuItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return MenuItem.reconstitute(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getAvailableOnlyInRestaurant(),
                entity.getPhotoPath(),
                restaurantMapper.toDomain(entity.getRestaurant())
        );
    }

    public MenuItemEntity toEntity(MenuItem domain) {
        if (domain == null) {
            return null;
        }
        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setAvailableOnlyInRestaurant(domain.getAvailableOnlyInRestaurant());
        entity.setPhotoPath(domain.getPhotoPath());
        entity.setRestaurant(restaurantMapper.toEntity(domain.getRestaurant()));
        return entity;
    }
}
