package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.UpdateUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UserTypeResponse;

import java.util.UUID;

public interface UpdateUserType {
    UserTypeResponse execute(UUID id, UpdateUserTypeRequest request);
}
