package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import com.tech_challange.grupo35.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRestaurantByIdUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private GetRestaurantByIdUseCase useCase;

    @Test
    void returnsRestaurantWhenFound() {
        UUID id = UUID.randomUUID();
        Restaurant restaurant = mock(Restaurant.class);
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));

        assertSame(restaurant, useCase.execute(id));
    }

    @Test
    void throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> useCase.execute(id));
    }
}
