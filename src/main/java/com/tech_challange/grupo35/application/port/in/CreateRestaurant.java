package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.domain.model.Restaurant;

public interface CreateRestaurant {
    Restaurant execute(CreateRestaurantRequest request);
}
