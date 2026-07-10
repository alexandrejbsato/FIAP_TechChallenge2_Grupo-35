package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.AssignUserTypeRequest;
import com.tech_challange.grupo35.application.port.in.AssignUserType;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.domain.exception.UserTypeNotFoundException;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AssignUserTypeUseCase implements AssignUserType {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;

    public static AssignUserTypeUseCase create(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        return new AssignUserTypeUseCase(userRepository, userTypeRepository);
    }

    @Override
    public User execute(UUID userId, AssignUserTypeRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        UserType userType = userTypeRepository.findById(request.userTypeId())
                .orElseThrow(() -> new UserTypeNotFoundException(request.userTypeId()));

        user.assignType(userType);
        return userRepository.save(user);
    }
}
