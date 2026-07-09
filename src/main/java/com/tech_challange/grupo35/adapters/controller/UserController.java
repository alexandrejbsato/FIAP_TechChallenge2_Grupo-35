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
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserController {

    private final CreateUser createUser;
    private final UpdateUser updateUser;
    private final ChangePassword changePassword;
    private final DeleteUser deleteUser;
    private final FindUsersByName findUsersByName;
    private final LoginUser loginUser;
    private final AssignUserType assignUserType;
    private final UserPresenter presenter;

    public static UserController create(CreateUser createUser, UpdateUser updateUser, ChangePassword changePassword,
            DeleteUser deleteUser, FindUsersByName findUsersByName, LoginUser loginUser, AssignUserType assignUserType,
            UserPresenter presenter) {
        return new UserController(createUser, updateUser, changePassword, deleteUser, findUsersByName, loginUser,
                assignUserType, presenter);
    }

    public UserResponse create(CreateUserRequest request) {
        return presenter.toResponse(createUser.execute(request));
    }

    public UserResponse update(UUID id, UpdateUserRequest request) {
        return presenter.toResponse(updateUser.execute(id, request));
    }

    public void changePassword(UUID id, ChangePasswordRequest request) {
        changePassword.execute(id, request);
    }

    public void delete(UUID id) {
        deleteUser.execute(id);
    }

    public List<UserResponse> findByName(String name) {
        return presenter.toResponseList(findUsersByName.execute(name));
    }

    public UserResponse assignUserType(UUID id, AssignUserTypeRequest request) {
        return presenter.toResponse(assignUserType.execute(id, request));
    }

    public LoginResponse login(LoginRequest request) {
        return loginUser.execute(request.login(), request.password());
    }
}
