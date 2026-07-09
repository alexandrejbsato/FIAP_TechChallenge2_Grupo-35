package com.tech_challange.grupo35.application.mapper;

import com.tech_challange.grupo35.application.dto.CreateMenuItemRequest;
import com.tech_challange.grupo35.application.dto.UpdateMenuItemRequest;
import com.tech_challange.grupo35.domain.model.MenuItem;
import com.tech_challange.grupo35.domain.model.Restaurant;

public class MenuItemMapper {

    public MenuItem toModel(CreateMenuItemRequest request, Restaurant restaurant) {
        return MenuItem.create(
                request.name(),
                request.description(),
                request.price(),
                request.availableOnlyInRestaurant(),
                request.photoPath(),
                restaurant
        );
    }

    public MenuItem updateModel(MenuItem current, UpdateMenuItemRequest request) {
        current.updateDetails(
                request.name(),
                request.description(),
                request.price(),
                request.availableOnlyInRestaurant(),
                request.photoPath()
        );
        return current;
    }
}
