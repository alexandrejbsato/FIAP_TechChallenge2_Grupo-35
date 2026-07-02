package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.CreateUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import com.tech_challange.grupo35.domain.exception.UserTypeNameAlreadyExistsException;
import com.tech_challange.grupo35.domain.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateUserTypeUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private CreateUserTypeUseCase useCase;

    @Test
    void createsUserTypeWhenNameIsAvailable() {
        UserType saved = new UserType();
        saved.setId(UUID.randomUUID());
        saved.setName("CUSTOMER");
        when(userTypeRepository.existsByName("CUSTOMER")).thenReturn(false);
        when(userTypeRepository.save(any(UserType.class))).thenReturn(saved);

        UserTypeResponse response = useCase.execute(new CreateUserTypeRequest("CUSTOMER"));

        assertEquals(saved.getId(), response.id());
        assertEquals("CUSTOMER", response.name());
        verify(userTypeRepository).save(any(UserType.class));
    }

    @Test
    void throwsWhenNameAlreadyExists() {
        when(userTypeRepository.existsByName("CUSTOMER")).thenReturn(true);

        assertThrows(UserTypeNameAlreadyExistsException.class,
                () -> useCase.execute(new CreateUserTypeRequest("CUSTOMER")));

        verify(userTypeRepository, never()).save(any());
    }
}
