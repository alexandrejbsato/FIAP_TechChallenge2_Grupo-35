package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.ChangePasswordRequest;
import com.tech_challange.grupo35.domain.exception.InvalidPasswordException;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.application.port.in.ChangePassword;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangePasswordUseCase implements ChangePassword {

    private final UserRepository userRepository;

    @Override
    public void execute(UUID id, ChangePasswordRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (!user.getPassword().equals(request.currentPassword())) {
            throw new InvalidPasswordException();
        }

        user.setPassword(request.newPassword());
        user.setLastUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}
