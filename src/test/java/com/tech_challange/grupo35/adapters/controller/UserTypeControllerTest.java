package com.tech_challange.grupo35.adapters.controller;

import com.tech_challange.grupo35.adapters.presenter.UserTypePresenter;
import com.tech_challange.grupo35.application.dto.CreateUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UpdateUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.application.port.in.CreateUserType;
import com.tech_challange.grupo35.application.port.in.DeleteUserType;
import com.tech_challange.grupo35.application.port.in.GetAllUserTypes;
import com.tech_challange.grupo35.application.port.in.GetUserTypeById;
import com.tech_challange.grupo35.application.port.in.GetUserTypeByName;
import com.tech_challange.grupo35.application.port.in.UpdateUserType;
import com.tech_challange.grupo35.domain.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserTypeControllerTest {

    @Mock
    private CreateUserType createUserType;
    @Mock
    private GetAllUserTypes getAllUserTypes;
    @Mock
    private GetUserTypeById getUserTypeById;
    @Mock
    private GetUserTypeByName getUserTypeByName;
    @Mock
    private UpdateUserType updateUserType;
    @Mock
    private DeleteUserType deleteUserType;
    @Mock
    private UserTypePresenter presenter;

    @InjectMocks
    private UserTypeController controller;

    @Test
    void createExecutesUseCaseThenPresentsEntity() {
        CreateUserTypeRequest request = new CreateUserTypeRequest("CUSTOMER");
        UserType entity = UserType.reconstitute(UUID.randomUUID(), "CUSTOMER");
        UserTypeResponse expected = new UserTypeResponse(entity.getId(), "CUSTOMER");
        when(createUserType.execute(request)).thenReturn(entity);
        when(presenter.toResponse(entity)).thenReturn(expected);

        UserTypeResponse result = controller.create(request);

        assertSame(expected, result);
        verify(createUserType).execute(request);
        verify(presenter).toResponse(entity);
    }

    @Test
    void findAllExecutesUseCaseThenPresentsList() {
        List<UserType> entities = List.of(UserType.reconstitute(UUID.randomUUID(), "CUSTOMER"));
        List<UserTypeResponse> expected = List.of(new UserTypeResponse(UUID.randomUUID(), "CUSTOMER"));
        when(getAllUserTypes.execute()).thenReturn(entities);
        when(presenter.toResponseList(entities)).thenReturn(expected);

        assertSame(expected, controller.findAll());
    }

    @Test
    void findByIdExecutesUseCaseThenPresentsEntity() {
        UUID id = UUID.randomUUID();
        UserType entity = UserType.reconstitute(id, "CUSTOMER");
        UserTypeResponse expected = new UserTypeResponse(id, "CUSTOMER");
        when(getUserTypeById.execute(id)).thenReturn(entity);
        when(presenter.toResponse(entity)).thenReturn(expected);

        assertSame(expected, controller.findById(id));
    }

    @Test
    void findByNameExecutesUseCaseThenPresentsEntity() {
        UserType entity = UserType.reconstitute(UUID.randomUUID(), "RESTAURANT_OWNER");
        UserTypeResponse expected = new UserTypeResponse(entity.getId(), "RESTAURANT_OWNER");
        when(getUserTypeByName.execute("RESTAURANT_OWNER")).thenReturn(entity);
        when(presenter.toResponse(entity)).thenReturn(expected);

        assertSame(expected, controller.findByName("RESTAURANT_OWNER"));
    }

    @Test
    void updateExecutesUseCaseThenPresentsEntity() {
        UUID id = UUID.randomUUID();
        UpdateUserTypeRequest request = new UpdateUserTypeRequest("NEW");
        UserType entity = UserType.reconstitute(id, "NEW");
        UserTypeResponse expected = new UserTypeResponse(id, "NEW");
        when(updateUserType.execute(id, request)).thenReturn(entity);
        when(presenter.toResponse(entity)).thenReturn(expected);

        assertSame(expected, controller.update(id, request));
    }

    @Test
    void deleteDelegatesToUseCase() {
        UUID id = UUID.randomUUID();

        controller.delete(id);

        verify(deleteUserType).execute(id);
    }
}
