package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.AssignUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UserResponse;

import java.util.UUID;

public interface AssignUserType {
    UserResponse execute(UUID userId, AssignUserTypeRequest request);
}
