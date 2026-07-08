package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import java.util.UUID;

public interface GetMenuItemById {
    MenuItemResponse execute(UUID restaurantId, UUID menuItemId);
}
