package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.DeleteUser;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase implements DeleteUser {

    private final UserRepository userRepository;

    @Override
    public void execute(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
