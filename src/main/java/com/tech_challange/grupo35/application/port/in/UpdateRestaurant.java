package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.domain.model.Restaurant;

import java.util.UUID;

public interface UpdateRestaurant {
    Restaurant execute(UUID id, UpdateRestaurantRequest request);
}
