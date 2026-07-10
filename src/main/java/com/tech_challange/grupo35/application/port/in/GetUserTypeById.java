package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.domain.model.UserType;

import java.util.UUID;

public interface GetUserTypeById {
    UserType execute(UUID id);
}
