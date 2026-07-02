package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.RestaurantResponse;

import java.util.List;

public interface GetAllRestaurants {
    List<RestaurantResponse> execute();
}
