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
class GetUserTypeByIdUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private GetUserTypeByIdUseCase useCase;

    @Test
    void returnsUserTypeWhenFound() {
        UUID id = UUID.randomUUID();
        UserType userType = new UserType();
        userType.setId(id);
        userType.setName("CUSTOMER");
        when(userTypeRepository.findById(id)).thenReturn(Optional.of(userType));

        UserTypeResponse response = useCase.execute(id);

        assertEquals(id, response.id());
        assertEquals("CUSTOMER", response.name());
    }

    @Test
    void throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(userTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class, () -> useCase.execute(id));
    }
}
