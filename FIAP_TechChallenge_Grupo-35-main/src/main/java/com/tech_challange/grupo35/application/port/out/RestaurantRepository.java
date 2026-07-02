package com.tech_challange.grupo35.application.port.out;

import com.tech_challange.grupo35.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant);
    Optional<Restaurant> findById(UUID id);
    List<Restaurant> findAll();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
