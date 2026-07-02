package com.tech_challange.grupo35.application.validation;

import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.domain.exception.InvalidRestaurantOwnerException;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserTypeNames;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestaurantOwnerValidator {

    private final UserRepository userRepository;

    public User validateAndGet(UUID ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new UserNotFoundException(ownerId));

        if (owner.getUserType() == null
                || !UserTypeNames.RESTAURANT_OWNER.equals(owner.getUserType().getName())) {
            throw new InvalidRestaurantOwnerException(ownerId);
        }
        return owner;
    }
}
