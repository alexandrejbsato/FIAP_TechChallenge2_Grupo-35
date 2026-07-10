package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.FindUsersByName;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.domain.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindUsersByNameUseCase implements FindUsersByName {

    private final UserRepository userRepository;

    public static FindUsersByNameUseCase create(UserRepository userRepository) {
        return new FindUsersByNameUseCase(userRepository);
    }

    @Override
    public List<User> execute(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
}
