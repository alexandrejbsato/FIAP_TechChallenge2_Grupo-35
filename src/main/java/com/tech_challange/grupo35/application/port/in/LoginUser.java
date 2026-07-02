package com.tech_challange.grupo35.application.port.in;

import com.tech_challange.grupo35.application.dto.LoginResponse;

public interface LoginUser {
    LoginResponse execute(String login, String password);
}
