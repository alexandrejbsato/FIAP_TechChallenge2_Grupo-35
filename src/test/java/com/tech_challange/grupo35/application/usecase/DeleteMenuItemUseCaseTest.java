package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.out.MenuItemRepository;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.MenuItemNotFoundException;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteMenuItemUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @InjectMocks
    private DeleteMenuItemUseCase useCase;

    @Test
    void deletesMenuItemWhenRestaurantAndItemExist() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        when(menuItemRepository.existsByIdAndRestaurantId(itemId, restaurantId)).thenReturn(true);

        useCase.execute(restaurantId, itemId);

        verify(menuItemRepository).deleteById(itemId);
    }

    @Test
    void throwsWhenRestaurantDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        when(restaurantRepository.existsById(restaurantId)).thenReturn(false);

        assertThrows(RestaurantNotFoundException.class, () -> useCase.execute(restaurantId, itemId));
        verify(menuItemRepository, never()).deleteById(any());
    }

    @Test
    void throwsWhenMenuItemDoesNotExist() {
        UUID restaurantId = UUID.randomUUID();
        UUID itemId = UUID.randomUUID();
        when(restaurantRepository.existsById(restaurantId)).thenReturn(true);
        when(menuItemRepository.existsByIdAndRestaurantId(itemId, restaurantId)).thenReturn(false);

        assertThrows(MenuItemNotFoundException.class, () -> useCase.execute(restaurantId, itemId));
        verify(menuItemRepository, never()).deleteById(any());
    }
}
