package com.tech_challange.grupo35.domain.exception;

import java.util.UUID;

public class InvalidRestaurantOwnerException extends RuntimeException {
    public InvalidRestaurantOwnerException(UUID ownerId) {
        super("O usuário informado como dono deve ser do tipo RESTAURANT_OWNER: " + ownerId);
    }
}
