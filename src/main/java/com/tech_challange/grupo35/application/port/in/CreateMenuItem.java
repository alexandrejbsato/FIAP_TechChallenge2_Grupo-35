package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.CreateMenuItemRequest;
import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import java.util.UUID;

public interface CreateMenuItem {
    MenuItemResponse execute(UUID restaurantId, CreateMenuItemRequest request);
}
