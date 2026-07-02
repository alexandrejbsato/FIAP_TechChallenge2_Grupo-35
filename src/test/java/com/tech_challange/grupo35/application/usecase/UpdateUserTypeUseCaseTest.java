package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.UpdateUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import com.tech_challange.grupo35.domain.exception.UserTypeNameAlreadyExistsException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserTypeUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UpdateUserTypeUseCase useCase;

    private UserType existing(UUID id, String name) {
        UserType userType = new UserType();
        userType.setId(id);
        userType.setName(name);
        return userType;
    }

    @Test
    void updatesNameWhenNewNameIsAvailable() {
        UUID id = UUID.randomUUID();
        when(userTypeRepository.findById(id)).thenReturn(Optional.of(existing(id, "OLD")));
        when(userTypeRepository.existsByName("NEW")).thenReturn(false);
        when(userTypeRepository.save(any(UserType.class))).thenAnswer(inv -> inv.getArgument(0));

        UserTypeResponse response = useCase.execute(id, new UpdateUserTypeRequest("NEW"));

        assertEquals("NEW", response.name());
        verify(userTypeRepository).save(any(UserType.class));
    }

    @Test
    void keepsSameNameWithoutUniquenessCheck() {
        UUID id = UUID.randomUUID();
        when(userTypeRepository.findById(id)).thenReturn(Optional.of(existing(id, "SAME")));
        when(userTypeRepository.save(any(UserType.class))).thenAnswer(inv -> inv.getArgument(0));

        UserTypeResponse response = useCase.execute(id, new UpdateUserTypeRequest("SAME"));

        assertEquals("SAME", response.name());
        verify(userTypeRepository, never()).existsByName(any());
    }

    @Test
    void throwsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(userTypeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UserTypeNotFoundException.class,
                () -> useCase.execute(id, new UpdateUserTypeRequest("NEW")));
        verify(userTypeRepository, never()).save(any());
    }

    @Test
    void throwsWhenNewNameAlreadyExists() {
        UUID id = UUID.randomUUID();
        when(userTypeRepository.findById(id)).thenReturn(Optional.of(existing(id, "OLD")));
        when(userTypeRepository.existsByName("TAKEN")).thenReturn(true);

        assertThrows(UserTypeNameAlreadyExistsException.class,
                () -> useCase.execute(id, new UpdateUserTypeRequest("TAKEN")));
        verify(userTypeRepository, never()).save(any());
    }
}
