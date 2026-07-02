package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.AddressDto;
import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.mapper.RestaurantMapper;
import com.tech_challange.grupo35.application.port.out.RestaurantRepository;
import com.tech_challange.grupo35.application.validation.RestaurantOwnerValidator;
import com.tech_challange.grupo35.domain.exception.InvalidRestaurantOwnerException;
import com.tech_challange.grupo35.domain.model.Restaurant;
import com.tech_challange.grupo35.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantOwnerValidator ownerValidator;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private CreateRestaurantUseCase useCase;

    private CreateRestaurantRequest request(UUID ownerId) {
        AddressDto address = new AddressDto("Rua A", "1", "Centro", "Cidade", "ST", "00000-000");
        return new CreateRestaurantRequest("Resto", address, "Italiana", "09-18", ownerId);
    }

    @Test
    void createsRestaurantWithValidatedOwner() {
        UUID ownerId = UUID.randomUUID();
        CreateRestaurantRequest request = request(ownerId);
        User owner = new User();
        Restaurant model = new Restaurant();
        Restaurant saved = new Restaurant();
        RestaurantResponse expected = new RestaurantResponse(UUID.randomUUID(), "Resto", null, "Italiana", "09-18", null);

        when(ownerValidator.validateAndGet(ownerId)).thenReturn(owner);
        when(restaurantMapper.toModel(request, owner)).thenReturn(model);
        when(restaurantRepository.save(model)).thenReturn(saved);
        when(restaurantMapper.toResponse(saved)).thenReturn(expected);

        RestaurantResponse response = useCase.execute(request);

        assertSame(expected, response);
        verify(restaurantRepository).save(model);
    }

    @Test
    void doesNotSaveWhenOwnerIsInvalid() {
        UUID ownerId = UUID.randomUUID();
        CreateRestaurantRequest request = request(ownerId);
        when(ownerValidator.validateAndGet(ownerId)).thenThrow(new InvalidRestaurantOwnerException(ownerId));

        assertThrows(InvalidRestaurantOwnerException.class, () -> useCase.execute(request));
        verify(restaurantRepository, never()).save(any());
    }
}
