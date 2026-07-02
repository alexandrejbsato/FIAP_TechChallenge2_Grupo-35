package com.tech_challange.grupo35.application.validation;

import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.domain.exception.InvalidRestaurantOwnerException;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.domain.model.UserTypeNames;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantOwnerValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RestaurantOwnerValidator validator;

    private User userWithType(String typeName) {
        User user = new User();
        user.setId(UUID.randomUUID());
        if (typeName != null) {
            UserType type = new UserType();
            type.setName(typeName);
            user.setUserType(type);
        }
        return user;
    }

    @Test
    void returnsOwnerWhenTypeIsRestaurantOwner() {
        UUID id = UUID.randomUUID();
        User owner = userWithType(UserTypeNames.RESTAURANT_OWNER);
        when(userRepository.findById(id)).thenReturn(Optional.of(owner));

        assertSame(owner, validator.validateAndGet(id));
    }

    @Test
    void throwsWhenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> validator.validateAndGet(id));
    }

    @Test
    void throwsWhenUserHasNoType() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.of(userWithType(null)));

        assertThrows(InvalidRestaurantOwnerException.class, () -> validator.validateAndGet(id));
    }

    @Test
    void throwsWhenUserTypeIsNotRestaurantOwner() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.of(userWithType(UserTypeNames.CUSTOMER)));

        assertThrows(InvalidRestaurantOwnerException.class, () -> validator.validateAndGet(id));
    }
}
