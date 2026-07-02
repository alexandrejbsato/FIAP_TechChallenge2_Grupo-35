package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import com.tech_challange.grupo35.domain.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllUserTypesUseCaseTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private GetAllUserTypesUseCase useCase;

    @Test
    void returnsMappedList() {
        UserType a = new UserType();
        a.setId(UUID.randomUUID());
        a.setName("CUSTOMER");
        UserType b = new UserType();
        b.setId(UUID.randomUUID());
        b.setName("RESTAURANT_OWNER");
        when(userTypeRepository.findAll()).thenReturn(List.of(a, b));

        List<UserTypeResponse> response = useCase.execute();

        assertEquals(2, response.size());
        assertEquals("CUSTOMER", response.get(0).name());
        assertEquals("RESTAURANT_OWNER", response.get(1).name());
    }

    @Test
    void returnsEmptyListWhenNoUserTypes() {
        when(userTypeRepository.findAll()).thenReturn(List.of());

        assertTrue(useCase.execute().isEmpty());
    }
}
