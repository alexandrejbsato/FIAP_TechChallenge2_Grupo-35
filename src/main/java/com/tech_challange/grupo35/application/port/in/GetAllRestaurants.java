package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.domain.model.Restaurant;

import java.util.List;

public interface GetAllRestaurants {
    List<Restaurant> execute();
}
