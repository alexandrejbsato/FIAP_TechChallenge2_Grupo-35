package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.ChangePasswordRequest;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.application.port.in.ChangePassword;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ChangePasswordUseCase implements ChangePassword {

    private final UserRepository userRepository;

    public static ChangePasswordUseCase create(UserRepository userRepository) {
        return new ChangePasswordUseCase(userRepository);
    }

    @Override
    public void execute(UUID id, ChangePasswordRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.changePassword(request.currentPassword(), request.newPassword());
        userRepository.save(user);
    }
}
