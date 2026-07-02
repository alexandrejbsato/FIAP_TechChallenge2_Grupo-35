package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.UpdateUserRequest;
import com.tech_challange.grupo35.application.dto.UserResponse;
import com.tech_challange.grupo35.application.mapper.UserMapper;
import com.tech_challange.grupo35.application.port.in.UpdateUser;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.domain.exception.CpfAlreadyExistsException;
import com.tech_challange.grupo35.domain.exception.EmailAlreadyExistsException;
import com.tech_challange.grupo35.domain.exception.LoginAlreadyExistsException;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase implements UpdateUser {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse execute(UUID id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (request.email() != null && !request.email().equals(user.getEmail())
                && userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }
        if (request.login() != null && !request.login().equals(user.getLogin())
                && userRepository.existsByLogin(request.login())) {
            throw new LoginAlreadyExistsException(request.login());
        }
        if (request.cpf() != null && !request.cpf().equals(user.getCpf())
                && userRepository.existsByCpf(request.cpf())) {
            throw new CpfAlreadyExistsException(request.cpf());
        }

        User updated = userMapper.updateModel(user, request);

        return userMapper.toResponse(userRepository.save(updated));
    }
}
