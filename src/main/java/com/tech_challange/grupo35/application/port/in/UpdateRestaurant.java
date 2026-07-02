package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;

import java.util.UUID;

public interface UpdateRestaurant {
    RestaurantResponse execute(UUID id, UpdateRestaurantRequest request);
}
