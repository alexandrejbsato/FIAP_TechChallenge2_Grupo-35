package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.DeleteRestaurant;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteRestaurantUseCase implements DeleteRestaurant {

    private final RestaurantRepository restaurantRepository;

    @Override
    public void execute(UUID id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException(id);
        }
        restaurantRepository.deleteById(id);
    }
}
