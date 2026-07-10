package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.GetAllRestaurants;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.model.Restaurant;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAllRestaurantsUseCase implements GetAllRestaurants {

    private final RestaurantRepository restaurantRepository;

    public static GetAllRestaurantsUseCase create(RestaurantRepository restaurantRepository) {
        return new GetAllRestaurantsUseCase(restaurantRepository);
    }

    @Override
    public List<Restaurant> execute() {
        return restaurantRepository.findAll();
    }
}
