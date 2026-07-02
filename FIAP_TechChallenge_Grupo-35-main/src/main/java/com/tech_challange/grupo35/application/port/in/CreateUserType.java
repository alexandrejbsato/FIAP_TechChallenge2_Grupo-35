package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.CreateUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UserTypeResponse;

public interface CreateUserType {
    UserTypeResponse execute(CreateUserTypeRequest request);
}
