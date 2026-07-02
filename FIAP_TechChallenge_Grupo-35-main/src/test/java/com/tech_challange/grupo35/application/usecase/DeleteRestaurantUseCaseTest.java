package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private DeleteRestaurantUseCase useCase;

    @Test
    void deletesWhenExists() {
        UUID id = UUID.randomUUID();
        when(restaurantRepository.existsById(id)).thenReturn(true);

        useCase.execute(id);

        verify(restaurantRepository).deleteById(id);
    }

    @Test
    void throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(restaurantRepository.existsById(id)).thenReturn(false);

        assertThrows(RestaurantNotFoundException.class, () -> useCase.execute(id));
        verify(restaurantRepository, never()).deleteById(any());
    }
}
