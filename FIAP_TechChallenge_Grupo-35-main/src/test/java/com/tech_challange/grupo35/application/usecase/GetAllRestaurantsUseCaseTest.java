package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.mapper.RestaurantMapper;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllRestaurantsUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private GetAllRestaurantsUseCase useCase;

    @Test
    void returnsMappedList() {
        Restaurant r1 = new Restaurant();
        r1.setId(UUID.randomUUID());
        Restaurant r2 = new Restaurant();
        r2.setId(UUID.randomUUID());
        RestaurantResponse resp1 = new RestaurantResponse(UUID.randomUUID(), "A", null, null, null, null);
        RestaurantResponse resp2 = new RestaurantResponse(UUID.randomUUID(), "B", null, null, null, null);
        when(restaurantRepository.findAll()).thenReturn(List.of(r1, r2));
        when(restaurantMapper.toResponse(r1)).thenReturn(resp1);
        when(restaurantMapper.toResponse(r2)).thenReturn(resp2);

        List<RestaurantResponse> response = useCase.execute();

        assertEquals(List.of(resp1, resp2), response);
    }

    @Test
    void returnsEmptyListWhenNoRestaurants() {
        when(restaurantRepository.findAll()).thenReturn(List.of());

        assertTrue(useCase.execute().isEmpty());
    }
}
