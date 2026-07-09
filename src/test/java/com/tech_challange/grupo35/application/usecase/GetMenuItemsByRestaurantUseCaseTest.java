package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.application.mapper.MenuItemMapper;
import com.tech_challange.grupo35.application.port.out.MenuItemRepository;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import com.tech_challange.grupo35.domain.model.MenuItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMenuItemsByRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private MenuItemMapper menuItemMapper;

    @InjectMocks
    private GetMenuItemsByRestaurantUseCase useCase;

    @Test
    void returnsMenuItemsWhenRestaurantExists() {
        UUID restaurantId = UUID.randomUUID();
        MenuItem item = MenuItem.reconstitute(UUID.randomUUID(), "Lasanha", "Massa fresca",
                BigDecimal.TEN, true, "/foto.jpg", null);
        MenuItemResponse response = new MenuItemResponse(UUID.randomUUID(), "Lasanha", "Massa fresca",
                BigDecimal.TEN, true, "/foto.jpg", restaurantId);

        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        when(menuItemRepository.findByRestaurantId(restaurantId)).thenReturn(List.of(item));
        when(menuItemMapper.toResponse(item)).thenReturn(response);

        List<MenuItemResponse> result = useCase.execute(restaurantId);

        assertEquals(List.of(response), result);
    }

    @Test
    void throwsWhenRestaurantDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        when(restaurantRepository.existsById(restaurantId)).thenReturn(false);

        assertThrows(RestaurantNotFoundException.class, () -> useCase.execute(restaurantId));
        verify(menuItemRepository, never()).findByRestaurantId(restaurantId);
    }
}
