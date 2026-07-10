package com.tech_challange.grupo35.application.port.in;

import java.util.UUID;

public interface DeleteMenuItem {
    void execute(UUID restaurantId, UUID menuItemId);
}
