package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.AssignUserTypeRequest;
import com.tech_challange.grupo35.domain.model.User;

import java.util.UUID;

public interface AssignUserType {
    User execute(UUID userId, AssignUserTypeRequest request);
}
