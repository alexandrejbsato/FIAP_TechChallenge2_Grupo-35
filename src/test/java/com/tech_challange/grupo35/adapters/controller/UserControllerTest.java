package com.tech_challange.grupo35.adapters.controller;

import com.tech_challange.grupo35.adapters.presenter.UserPresenter;
import com.tech_challange.grupo35.application.dto.AssignUserTypeRequest;
import com.tech_challange.grupo35.application.dto.ChangePasswordRequest;
import com.tech_challange.grupo35.application.dto.CreateUserRequest;
import com.tech_challange.grupo35.application.dto.LoginRequest;
import com.tech_challange.grupo35.application.dto.LoginResponse;
import com.tech_challange.grupo35.application.dto.UpdateUserRequest;
import com.tech_challange.grupo35.application.dto.UserResponse;
import com.tech_challange.grupo35.application.port.in.AssignUserType;
import com.tech_challange.grupo35.application.port.in.ChangePassword;
import com.tech_challange.grupo35.application.port.in.CreateUser;
import com.tech_challange.grupo35.application.port.in.DeleteUser;
import com.tech_challange.grupo35.application.port.in.FindUsersByName;
import com.tech_challange.grupo35.application.port.in.LoginUser;
import com.tech_challange.grupo35.application.port.in.UpdateUser;
import com.tech_challange.grupo35.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private CreateUser createUser;
    @Mock
    private UpdateUser updateUser;
    @Mock
    private ChangePassword changePassword;
    @Mock
    private DeleteUser deleteUser;
    @Mock
    private FindUsersByName findUsersByName;
    @Mock
    private LoginUser loginUser;
    @Mock
    private AssignUserType assignUserType;
    @Mock
    private UserPresenter presenter;

    @InjectMocks
    private UserController controller;

    private User entity(String name) {
        return User.reconstitute(UUID.randomUUID(), name, name + "@mail.com", name, "secret",
                null, "123.456.789-00", LocalDateTime.now(), null);
    }

    @Test
    void createExecutesUseCaseThenPresentsEntity() {
        CreateUserRequest request = new CreateUserRequest("joao", "joao@mail.com", "joao", "pwd", null, "123");
        User user = entity("joao");
        UserResponse expected = new UserResponse(user.getId(), "joao", null, null, null, null, null, null, null);
        when(createUser.execute(request)).thenReturn(user);
        when(presenter.toResponse(user)).thenReturn(expected);

        assertSame(expected, controller.create(request));
        verify(createUser).execute(request);
    }

    @Test
    void updateExecutesUseCaseThenPresentsEntity() {
        UUID id = UUID.randomUUID();
        UpdateUserRequest request = new UpdateUserRequest("joao", null, null, null, null);
        User user = entity("joao");
        UserResponse expected = new UserResponse(id, "joao", null, null, null, null, null, null, null);
        when(updateUser.execute(id, request)).thenReturn(user);
        when(presenter.toResponse(user)).thenReturn(expected);

        assertSame(expected, controller.update(id, request));
    }

    @Test
    void findByNameExecutesUseCaseThenPresentsList() {
        List<User> users = List.of(entity("joao"));
        List<UserResponse> expected = List.of(new UserResponse(UUID.randomUUID(), "joao", null, null, null, null, null, null, null));
        when(findUsersByName.execute("joao")).thenReturn(users);
        when(presenter.toResponseList(users)).thenReturn(expected);

        assertSame(expected, controller.findByName("joao"));
    }

    @Test
    void assignUserTypeExecutesUseCaseThenPresentsEntity() {
        UUID id = UUID.randomUUID();
        AssignUserTypeRequest request = new AssignUserTypeRequest(UUID.randomUUID());
        User user = entity("joao");
        UserResponse expected = new UserResponse(id, "joao", null, null, null, null, null, null, null);
        when(assignUserType.execute(id, request)).thenReturn(user);
        when(presenter.toResponse(user)).thenReturn(expected);

        assertSame(expected, controller.assignUserType(id, request));
    }

    @Test
    void changePasswordDelegatesToUseCase() {
        UUID id = UUID.randomUUID();
        ChangePasswordRequest request = new ChangePasswordRequest("old", "new");

        controller.changePassword(id, request);

        verify(changePassword).execute(id, request);
        verifyNoInteractions(presenter);
    }

    @Test
    void deleteDelegatesToUseCase() {
        UUID id = UUID.randomUUID();

        controller.delete(id);

        verify(deleteUser).execute(id);
        verifyNoInteractions(presenter);
    }

    @Test
    void loginDelegatesToUseCaseWithoutPresenter() {
        LoginRequest request = new LoginRequest("joao", "pwd");
        LoginResponse expected = new LoginResponse("jwt-token");
        when(loginUser.execute("joao", "pwd")).thenReturn(expected);

        assertSame(expected, controller.login(request));
        verifyNoInteractions(presenter);
    }
}
