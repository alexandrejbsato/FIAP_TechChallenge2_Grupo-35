package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.LoginResponse;
import com.tech_challange.grupo35.application.port.out.TokenService;
import com.tech_challange.grupo35.application.port.in.LoginUser;
import com.tech_challange.grupo35.domain.exception.InvalidPasswordException;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUseCase implements LoginUser {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Override
    public LoginResponse execute(String login, String password) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(UserNotFoundException::new);

        if (!user.getPassword().equals(password)) {
            throw new InvalidPasswordException();
        }

        return new LoginResponse(tokenService.generateToken(login));
    }
}
