package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import java.util.List;
import java.util.UUID;

public interface GetMenuItemsByRestaurant {
    List<MenuItemResponse> execute(UUID restaurantId);
}
