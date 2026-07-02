package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import com.tech_challange.grupo35.domain.exception.UserTypeNotFoundException;
import com.tech_challange.grupo35.domain.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetUserTypeByNameUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private GetUserTypeByNameUseCase useCase;

    @Test
    void returnsUserTypeWhenFound() {
        UserType userType = new UserType();
        userType.setId(UUID.randomUUID());
        userType.setName("RESTAURANT_OWNER");
        when(userTypeRepository.findByName("RESTAURANT_OWNER")).thenReturn(Optional.of(userType));

        UserTypeResponse response = useCase.execute("RESTAURANT_OWNER");

        assertEquals("RESTAURANT_OWNER", response.name());
    }

    @Test
    void throwsWhenNotFound() {
        when(userTypeRepository.findByName("MISSING")).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> useCase.execute("MISSING"));
    }
}
