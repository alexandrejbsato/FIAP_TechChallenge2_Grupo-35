package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;

public interface CreateRestaurant {
    RestaurantResponse execute(CreateRestaurantRequest request);
}
