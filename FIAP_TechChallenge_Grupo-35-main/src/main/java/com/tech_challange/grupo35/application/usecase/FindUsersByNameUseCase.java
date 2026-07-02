package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.dto.UserResponse;
import com.tech_challange.grupo35.application.mapper.UserMapper;
import com.tech_challange.grupo35.application.port.in.FindUsersByName;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindUsersByNameUseCase implements FindUsersByName {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> execute(String name) {
        return userRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }
}
