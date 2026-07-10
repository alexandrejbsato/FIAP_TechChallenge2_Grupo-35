package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.mapper.RestaurantMapper;
import com.tech_challange.grupo35.application.port.in.CreateRestaurant;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateRestaurantUseCase implements CreateRestaurant {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RestaurantMapper restaurantMapper;

    public static CreateRestaurantUseCase create(RestaurantRepository restaurantRepository,
            UserRepository userRepository, RestaurantMapper restaurantMapper) {
        return new CreateRestaurantUseCase(restaurantRepository, userRepository, restaurantMapper);
    }

    @Override
    public Restaurant execute(CreateRestaurantRequest request) {
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new UserNotFoundException(request.ownerId()));
        Restaurant restaurant = restaurantMapper.toModel(request, owner);
        return restaurantRepository.save(restaurant);
    }
}
