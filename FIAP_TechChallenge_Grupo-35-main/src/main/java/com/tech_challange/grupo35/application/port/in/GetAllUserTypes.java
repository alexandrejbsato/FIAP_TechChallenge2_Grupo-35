package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.UserTypeResponse;

import java.util.List;

public interface GetAllUserTypes {
    List<UserTypeResponse> execute();
}
