package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.AssignUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UserResponse;
import com.tech_challange.grupo35.application.mapper.UserMapper;
import com.tech_challange.grupo35.application.port.in.AssignUserType;
import com.tech_challange.grupo35.domain.exception.UserNotFoundException;
import com.tech_challange.grupo35.domain.exception.UserTypeNotFoundException;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssignUserTypeUseCase implements AssignUserType {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse execute(UUID userId, AssignUserTypeRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        UserType userType = userTypeRepository.findById(request.userTypeId())
                .orElseThrow(() -> new UserTypeNotFoundException(request.userTypeId()));

        user.setUserType(userType);
        return userMapper.toResponse(userRepository.save(user));
    }
}
