package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.AddressDto;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.application.mapper.RestaurantMapper;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.domain.exception.RestaurantNotFoundException;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private UpdateRestaurantUseCase useCase;

    private UpdateRestaurantRequest request(UUID ownerId) {
        AddressDto address = new AddressDto("Rua A", "1", "Centro", "Cidade", "ST", "00000-000");
        return new UpdateRestaurantRequest("Resto", address, "Italiana", "09-18", ownerId);
    }

    @Test
    void updatesRestaurantWhenFoundAndOwnerExists() {
        UUID id = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        UpdateRestaurantRequest request = request(ownerId);
        Restaurant current = mock(Restaurant.class);
        User owner = mock(User.class);
        Restaurant updated = mock(Restaurant.class);
        Restaurant saved = mock(Restaurant.class);
        RestaurantResponse expected = new RestaurantResponse(id, "Resto", null, "Italiana", "09-18", null);

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(current));
        when(userRepository.findById(ownerId)).thenReturn(Optional.of(owner));
        when(restaurantMapper.updateModel(current, request, owner)).thenReturn(updated);
        when(restaurantRepository.save(updated)).thenReturn(saved);
        when(restaurantMapper.toResponse(saved)).thenReturn(expected);

        RestaurantResponse response = useCase.execute(id, request);

        assertSame(expected, response);
        verify(restaurantRepository).save(updated);
    }

    @Test
    void throwsWhenRestaurantNotFound() {
        UUID id = UUID.randomUUID();
        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> useCase.execute(id, request(UUID.randomUUID())));
        verify(userRepository, never()).findById(any());
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    void doesNotSaveWhenOwnerNotFound() {
        UUID id = UUID.randomUUID();
        UUID ownerId = UUID.randomUUID();
        when(restaurantRepository.findById(id)).thenReturn(Optional.of(mock(Restaurant.class)));
        when(userRepository.findById(ownerId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> useCase.execute(id, request(ownerId)));
        verify(restaurantRepository, never()).save(any());
    }
}
