package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.UpdateMenuItemRequest;
import com.tech_challange.grupo35.domain.model.MenuItem;
import java.util.UUID;

public interface UpdateMenuItem {
    MenuItem execute(UUID restaurantId, UUID menuItemId, UpdateMenuItemRequest request);
}
