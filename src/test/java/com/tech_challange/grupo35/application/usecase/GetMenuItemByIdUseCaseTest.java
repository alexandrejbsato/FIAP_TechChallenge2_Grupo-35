package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.application.mapper.MenuItemMapper;
import com.tech_challange.grupo35.application.port.out.MenuItemRepository;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.MenuItemNotFoundException;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import com.tech_challange.grupo35.domain.model.MenuItem;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMenuItemByIdUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private MenuItemMapper menuItemMapper;

    @InjectMocks
    private GetMenuItemByIdUseCase useCase;

    @Test
    void returnsMenuItemWhenRestaurantAndItemExist() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        MenuItem item = new MenuItem();
        MenuItemResponse expected = new MenuItemResponse(itemId, "Lasanha", "Massa fresca",
                BigDecimal.TEN, true, "/foto.jpg", restaurantId);

        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        when(menuItemRepository.findByIdAndRestaurantId(itemId, restaurantId)).thenReturn(Optional.of(item));
        when(menuItemMapper.toResponse(item)).thenReturn(expected);

        MenuItemResponse response = useCase.execute(restaurantId, itemId);

        assertSame(expected, response);
    }

    @Test
    void throwsWhenRestaurantDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        when(restaurantRepository.existsById(restaurantId)).thenReturn(false);

        assertThrows(RestaurantNotFoundException.class, () -> useCase.execute(restaurantId, itemId));
        verify(menuItemRepository, never()).findByIdAndRestaurantId(itemId, restaurantId);
    }

    @Test
    void throwsWhenMenuItemDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        when(menuItemRepository.findByIdAndRestaurantId(itemId, restaurantId)).thenReturn(Optional.empty());

        assertThrows(MenuItemNotFoundException.class, () -> useCase.execute(restaurantId, itemId));
    }
}
