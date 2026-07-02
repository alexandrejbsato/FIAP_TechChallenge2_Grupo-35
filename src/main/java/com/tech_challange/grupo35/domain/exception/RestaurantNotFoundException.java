package com.tech_challange.grupo35.domain.exception;

import java.util.UUID;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(UUID id) {
        super("Restaurante não encontrado com id: " + id);
    }
}
