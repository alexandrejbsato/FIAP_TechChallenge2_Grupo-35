package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.application.mapper.RestaurantMapper;
import com.tech_challange.grupo35.application.port.in.UpdateRestaurant;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateRestaurantUseCase implements UpdateRestaurant {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RestaurantMapper restaurantMapper;

    public static UpdateRestaurantUseCase create(RestaurantRepository restaurantRepository,
            UserRepository userRepository, RestaurantMapper restaurantMapper) {
        return new UpdateRestaurantUseCase(restaurantRepository, userRepository, restaurantMapper);
    }

    @Override
    public Restaurant execute(UUID id, UpdateRestaurantRequest request) {
        Restaurant current = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new UserNotFoundException(request.ownerId()));
        Restaurant updated = restaurantMapper.updateModel(current, request, owner);
        return restaurantRepository.save(updated);
    }
}
