package com.tech_challange.grupo35.application.usecase;

import com.tech_challange.grupo35.application.port.in.DeleteUserType;
import com.tech_challange.grupo35.domain.exception.UserTypeInUseException;
import com.tech_challange.grupo35.domain.exception.UserTypeNotFoundException;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteUserTypeUseCase implements DeleteUserType {

    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;

    public static DeleteUserTypeUseCase create(UserTypeRepository userTypeRepository, UserRepository userRepository) {
        return new DeleteUserTypeUseCase(userTypeRepository, userRepository);
    }

    @Override
    public void execute(UUID id) {
        if (!userTypeRepository.existsById(id)) {
            throw new UserTypeNotFoundException(id);
        }
        if (userRepository.existsByUserTypeId(id)) {
            throw new UserTypeInUseException(id);
        }
        userTypeRepository.deleteById(id);
    }
}
