package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.UpdateUserRequest;
import com.tech_challange.grupo35.domain.model.User;

import java.util.UUID;

public interface UpdateUser {
    User execute(UUID id, UpdateUserRequest request);
}
