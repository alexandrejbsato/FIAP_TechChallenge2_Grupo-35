package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.UpdateUserTypeRequest;
import com.tech_challange.grupo35.domain.model.UserType;

import java.util.UUID;

public interface UpdateUserType {
    UserType execute(UUID id, UpdateUserTypeRequest request);
}
