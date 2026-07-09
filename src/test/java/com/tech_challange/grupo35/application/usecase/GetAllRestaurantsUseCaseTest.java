package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllRestaurantsUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private GetAllRestaurantsUseCase useCase;

    @Test
    void returnsAllRestaurants() {
        Restaurant r1 = mock(Restaurant.class);
        Restaurant r2 = mock(Restaurant.class);
        when(restaurantRepository.findAll()).thenReturn(List.of(r1, r2));

        assertEquals(List.of(r1, r2), useCase.execute());
    }

    @Test
    void returnsEmptyListWhenNoRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(List.of());

        assertTrue(useCase.execute().isEmpty());
    }
}
