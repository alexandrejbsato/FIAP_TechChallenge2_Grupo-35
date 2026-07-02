package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import com.tech_challange.grupo35.domain.exception.UserTypeInUseException;
import com.tech_challange.grupo35.domain.exception.UserTypeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteUserTypeUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserTypeUseCase useCase;

    @Test
    void deletesWhenExistsAndNotInUse() {
        UUID id = UUID.randomUUID();
        when(userTypeRepository.existsById(id)).thenReturn(true);
        when(userRepository.existsByUserTypeId(id)).thenReturn(false);

        useCase.execute(id);

        verify(userTypeRepository).deleteById(id);
    }

    @Test
    void throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(userTypeRepository.existsById(id)).thenReturn(false);

        assertThrows(UserTypeNotFoundException.class, () -> useCase.execute(id));
        verify(userTypeRepository, never()).deleteById(any());
    }

    @Test
    void throwsWhenInUse() {
        UUID id = UUID.randomUUID();
        when(userTypeRepository.existsById(id)).thenReturn(true);
        when(userRepository.existsByUserTypeId(id)).thenReturn(true);

        assertThrows(UserTypeInUseException.class, () -> useCase.execute(id));
        verify(userTypeRepository, never()).deleteById(any());
    }
}
