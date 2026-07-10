package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.CreateUserTypeRequest;
import com.tech_challange.grupo35.domain.model.UserType;

public interface CreateUserType {
    UserType execute(CreateUserTypeRequest request);
}
