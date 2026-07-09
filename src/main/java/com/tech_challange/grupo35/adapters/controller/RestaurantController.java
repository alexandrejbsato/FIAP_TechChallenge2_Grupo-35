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
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component // temporário; removido no Plano 2
@RequiredArgsConstructor
public class RestaurantController {

    private final CreateRestaurant createRestaurant;
    private final GetAllRestaurants getAllRestaurants;
    private final GetRestaurantById getRestaurantById;
    private final UpdateRestaurant updateRestaurant;
    private final DeleteRestaurant deleteRestaurant;
    private final RestaurantPresenter presenter;

    public RestaurantResponse create(CreateRestaurantRequest request) {
        return presenter.toResponse(createRestaurant.execute(request));
    }

    public List<RestaurantResponse> findAll() {
        return presenter.toResponseList(getAllRestaurants.execute());
    }

    public RestaurantResponse findById(UUID id) {
        return presenter.toResponse(getRestaurantById.execute(id));
    }

    public RestaurantResponse update(UUID id, UpdateRestaurantRequest request) {
        return presenter.toResponse(updateRestaurant.execute(id, request));
    }

    public void delete(UUID id) {
        deleteRestaurant.execute(id);
    }
}
