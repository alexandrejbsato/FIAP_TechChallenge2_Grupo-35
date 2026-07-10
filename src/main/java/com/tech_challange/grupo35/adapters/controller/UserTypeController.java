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
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserTypeController {

    private final CreateUserType createUserType;
    private final GetAllUserTypes getAllUserTypes;
    private final GetUserTypeById getUserTypeById;
    private final GetUserTypeByName getUserTypeByName;
    private final UpdateUserType updateUserType;
    private final DeleteUserType deleteUserType;
    private final UserTypePresenter presenter;

    public static UserTypeController newInstance(CreateUserType createUserType, GetAllUserTypes getAllUserTypes,
            GetUserTypeById getUserTypeById, GetUserTypeByName getUserTypeByName, UpdateUserType updateUserType,
            DeleteUserType deleteUserType, UserTypePresenter presenter) {
        return new UserTypeController(createUserType, getAllUserTypes, getUserTypeById, getUserTypeByName,
                updateUserType, deleteUserType, presenter);
    }

    public UserTypeResponse create(CreateUserTypeRequest request) {
        return presenter.toResponse(createUserType.execute(request));
    }

    public List<UserTypeResponse> findAll() {
        return presenter.toResponseList(getAllUserTypes.execute());
    }

    public UserTypeResponse findByName(String name) {
        return presenter.toResponse(getUserTypeByName.execute(name));
    }

    public UserTypeResponse findById(UUID id) {
        return presenter.toResponse(getUserTypeById.execute(id));
    }

    public UserTypeResponse update(UUID id, UpdateUserTypeRequest request) {
        return presenter.toResponse(updateUserType.execute(id, request));
    }

    public void delete(UUID id) {
        deleteUserType.execute(id);
    }
}
