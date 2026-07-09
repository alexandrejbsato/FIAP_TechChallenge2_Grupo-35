package com.tech_challange.grupo35.adapters.controller;

import com.tech_challange.grupo35.adapters.presenter.RestaurantPresenter;
import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.application.port.in.CreateRestaurant;
import com.tech_challange.grupo35.application.port.in.DeleteRestaurant;
import com.tech_challange.grupo35.application.port.in.GetAllRestaurants;
import com.tech_challange.grupo35.application.port.in.GetRestaurantById;
import com.tech_challange.grupo35.application.port.in.UpdateRestaurant;
import com.tech_challange.grupo35.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {

    @Mock
    private CreateRestaurant createRestaurant;
    @Mock
    private GetAllRestaurants getAllRestaurants;
    @Mock
    private GetRestaurantById getRestaurantById;
    @Mock
    private UpdateRestaurant updateRestaurant;
    @Mock
    private DeleteRestaurant deleteRestaurant;
    @Mock
    private RestaurantPresenter presenter;

    @InjectMocks
    private RestaurantController controller;

    @Test
    void createExecutesUseCaseThenPresentsEntity() {
        CreateRestaurantRequest request = new CreateRestaurantRequest("Resto", null, null, null, UUID.randomUUID());
        Restaurant entity = mock(Restaurant.class);
        RestaurantResponse expected = new RestaurantResponse(UUID.randomUUID(), "Resto", null, null, null, null);
        when(createRestaurant.execute(request)).thenReturn(entity);
        when(presenter.toResponse(entity)).thenReturn(expected);

        assertSame(expected, controller.create(request));
        verify(createRestaurant).execute(request);
    }

    @Test
    void findAllExecutesUseCaseThenPresentsList() {
        List<Restaurant> entities = List.of(mock(Restaurant.class));
        List<RestaurantResponse> expected = List.of(new RestaurantResponse(UUID.randomUUID(), "Resto", null, null, null, null));
        when(getAllRestaurants.execute()).thenReturn(entities);
        when(presenter.toResponseList(entities)).thenReturn(expected);

        assertSame(expected, controller.findAll());
    }

    @Test
    void findByIdExecutesUseCaseThenPresentsEntity() {
        UUID id = UUID.randomUUID();
        Restaurant entity = mock(Restaurant.class);
        RestaurantResponse expected = new RestaurantResponse(id, "Resto", null, null, null, null);
        when(getRestaurantById.execute(id)).thenReturn(entity);
        when(presenter.toResponse(entity)).thenReturn(expected);

        assertSame(expected, controller.findById(id));
    }

    @Test
    void updateExecutesUseCaseThenPresentsEntity() {
        UUID id = UUID.randomUUID();
        UpdateRestaurantRequest request = new UpdateRestaurantRequest("Resto", null, null, null, UUID.randomUUID());
        Restaurant entity = mock(Restaurant.class);
        RestaurantResponse expected = new RestaurantResponse(id, "Resto", null, null, null, null);
        when(updateRestaurant.execute(id, request)).thenReturn(entity);
        when(presenter.toResponse(entity)).thenReturn(expected);

        assertSame(expected, controller.update(id, request));
    }

    @Test
    void deleteDelegatesToUseCase() {
        UUID id = UUID.randomUUID();

        controller.delete(id);

        verify(deleteRestaurant).execute(id);
        verifyNoInteractions(presenter);
    }
}
