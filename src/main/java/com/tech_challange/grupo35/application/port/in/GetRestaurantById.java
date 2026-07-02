package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.RestaurantResponse;

import java.util.UUID;

public interface GetRestaurantById {
    RestaurantResponse execute(UUID id);
}
