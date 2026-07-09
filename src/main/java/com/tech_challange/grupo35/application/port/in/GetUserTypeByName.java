package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.domain.model.UserType;

public interface GetUserTypeByName {
    UserType execute(String name);
}
