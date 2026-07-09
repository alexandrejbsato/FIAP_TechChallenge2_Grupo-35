package com.tech_challange.grupo35.adapters.presenter;

import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.domain.model.MenuItem;
import com.tech_challange.grupo35.domain.model.Restaurant;
import java.util.List;
import org.springframework.stereotype.Component;

@Component // temporário; removido no Plano 2
public class MenuItemPresenter {

    public MenuItemResponse toResponse(MenuItem item) {
        Restaurant restaurant = item.getRestaurant();
        return new MenuItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAvailableOnlyInRestaurant(),
                item.getPhotoPath(),
                restaurant != null ? restaurant.getId() : null
        );
    }

    public List<MenuItemResponse> toResponseList(List<MenuItem> items) {
        return items.stream().map(this::toResponse).toList();
    }
}
