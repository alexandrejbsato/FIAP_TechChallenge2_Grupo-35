package com.tech_challange.grupo35.application.mapper;

import com.tech_challange.grupo35.application.dto.CreateMenuItemRequest;
import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.application.dto.UpdateMenuItemRequest;
import com.tech_challange.grupo35.domain.model.MenuItem;
import com.tech_challange.grupo35.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    public MenuItem toModel(CreateMenuItemRequest request, Restaurant restaurant) {
        MenuItem menuItem = new MenuItem();
        menuItem.setName(request.name());
        menuItem.setDescription(request.description());
        menuItem.setPrice(request.price());
        menuItem.setAvailableOnlyInRestaurant(request.availableOnlyInRestaurant());
        menuItem.setPhotoPath(request.photoPath());
        menuItem.setRestaurant(restaurant);
        return menuItem;
    }

    public MenuItem updateModel(MenuItem current, UpdateMenuItemRequest request) {
        current.setName(request.name());
        current.setDescription(request.description());
        current.setPrice(request.price());
        current.setAvailableOnlyInRestaurant(request.availableOnlyInRestaurant());
        current.setPhotoPath(request.photoPath());
        return current;
    }

    public MenuItemResponse toResponse(MenuItem menuItem) {
        Restaurant restaurant = menuItem.getRestaurant();
        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getAvailableOnlyInRestaurant(),
                menuItem.getPhotoPath(),
                restaurant != null ? restaurant.getId() : null
        );
    }
}
