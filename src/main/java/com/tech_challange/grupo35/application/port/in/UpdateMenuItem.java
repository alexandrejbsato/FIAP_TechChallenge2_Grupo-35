package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.application.dto.UpdateMenuItemRequest;
import java.util.UUID;

public interface UpdateMenuItem {
    MenuItemResponse execute(UUID restaurantId, UUID menuItemId, UpdateMenuItemRequest request);
}
