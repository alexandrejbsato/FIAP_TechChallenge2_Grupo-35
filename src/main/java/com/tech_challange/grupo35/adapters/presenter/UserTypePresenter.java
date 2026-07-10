package com.tech_challange.grupo35.adapters.presenter;

import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.domain.model.UserType;
import java.util.List;

public class UserTypePresenter {

    public UserTypeResponse toResponse(UserType userType) {
        return new UserTypeResponse(userType.getId(), userType.getName());
    }

    public List<UserTypeResponse> toResponseList(List<UserType> userTypes) {
        return userTypes.stream().map(this::toResponse).toList();
    }
}
