package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.CreateMenuItemRequest;
import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.application.mapper.MenuItemMapper;
import com.tech_challange.grupo35.application.port.out.MenuItemRepository;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import com.tech_challange.grupo35.domain.model.MenuItem;
import com.tech_challange.grupo35.domain.model.Restaurant;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateMenuItemUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private MenuItemMapper menuItemMapper;

    @InjectMocks
    private CreateMenuItemUseCase useCase;

    private CreateMenuItemRequest request() {
        return new CreateMenuItemRequest("Lasanha", "Massa fresca", BigDecimal.valueOf(49.90), true, "/foto.jpg");
    }

    @Test
    void createsMenuItemWhenRestaurantExists() {
        UUID restaurantId = UUID.randomUUID();
        CreateMenuItemRequest request = request();
        Restaurant restaurant = Restaurant.reconstitute(restaurantId, "Resto", null, "Italiana", "09-18", null);
        MenuItem model = MenuItem.reconstitute(null, "Lasanha", "Massa fresca",
                BigDecimal.valueOf(49.90), true, "/foto.jpg", restaurant);
        MenuItem saved = MenuItem.reconstitute(UUID.randomUUID(), "Lasanha", "Massa fresca",
                BigDecimal.valueOf(49.90), true, "/foto.jpg", restaurant);
        MenuItemResponse expected = new MenuItemResponse(UUID.randomUUID(), "Lasanha", "Massa fresca",
                BigDecimal.valueOf(49.90), true, "/foto.jpg", restaurantId);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemMapper.toModel(request, restaurant)).thenReturn(model);
        when(menuItemRepository.save(model)).thenReturn(saved);
        when(menuItemMapper.toResponse(saved)).thenReturn(expected);

        MenuItemResponse response = useCase.execute(restaurantId, request);

        assertSame(expected, response);
        verify(menuItemRepository).save(model);
    }

    @Test
    void doesNotSaveWhenRestaurantDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> useCase.execute(restaurantId, request()));
        verify(menuItemRepository, never()).save(any());
    }
}
