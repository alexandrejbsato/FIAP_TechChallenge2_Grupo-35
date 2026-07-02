package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.CreateUserRequest;
import com.tech_challange.grupo35.application.dto.UserResponse;
import com.tech_challange.grupo35.application.mapper.UserMapper;
import com.tech_challange.grupo35.application.port.in.CreateUser;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.domain.exception.CpfAlreadyExistsException;
import com.tech_challange.grupo35.domain.exception.EmailAlreadyExistsException;
import com.tech_challange.grupo35.domain.exception.LoginAlreadyExistsException;
import com.tech_challange.grupo35.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase implements CreateUser {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse execute(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }
        if (userRepository.existsByLogin(request.login())) {
            throw new LoginAlreadyExistsException(request.login());
        }
        if (userRepository.existsByCpf(request.cpf())) {
            throw new CpfAlreadyExistsException(request.cpf());
        }

        User user = userMapper.toModel(request);

        return userMapper.toResponse(userRepository.save(user));
    }
}
