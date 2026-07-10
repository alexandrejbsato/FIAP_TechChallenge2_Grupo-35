package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.domain.model.UserType;

import java.util.List;

public interface GetAllUserTypes {
    List<UserType> execute();
}
