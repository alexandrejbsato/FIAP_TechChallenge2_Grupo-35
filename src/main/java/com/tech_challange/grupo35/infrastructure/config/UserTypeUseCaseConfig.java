package com.tech_challange.grupo35.infrastructure.config;

import com.tech_challange.grupo35.application.port.in.CreateUserType;
import com.tech_challange.grupo35.application.port.in.DeleteUserType;
import com.tech_challange.grupo35.application.port.in.GetAllUserTypes;
import com.tech_challange.grupo35.application.port.in.GetUserTypeById;
import com.tech_challange.grupo35.application.port.in.GetUserTypeByName;
import com.tech_challange.grupo35.application.port.in.UpdateUserType;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import com.tech_challange.grupo35.application.usecase.CreateUserTypeUseCase;
import com.tech_challange.grupo35.application.usecase.DeleteUserTypeUseCase;
import com.tech_challange.grupo35.application.usecase.GetAllUserTypesUseCase;
import com.tech_challange.grupo35.application.usecase.GetUserTypeByIdUseCase;
import com.tech_challange.grupo35.application.usecase.GetUserTypeByNameUseCase;
import com.tech_challange.grupo35.application.usecase.UpdateUserTypeUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserTypeUseCaseConfig {

    @Bean
    public CreateUserType createUserType(UserTypeRepository userTypeRepository) {
        return CreateUserTypeUseCase.create(userTypeRepository);
    }

    @Bean
    public UpdateUserType updateUserType(UserTypeRepository userTypeRepository) {
        return UpdateUserTypeUseCase.create(userTypeRepository);
    }

    @Bean
    public GetUserTypeById getUserTypeById(UserTypeRepository userTypeRepository) {
        return GetUserTypeByIdUseCase.create(userTypeRepository);
    }

    @Bean
    public GetUserTypeByName getUserTypeByName(UserTypeRepository userTypeRepository) {
        return GetUserTypeByNameUseCase.create(userTypeRepository);
    }

    @Bean
    public GetAllUserTypes getAllUserTypes(UserTypeRepository userTypeRepository) {
        return GetAllUserTypesUseCase.create(userTypeRepository);
    }

    @Bean
    public DeleteUserType deleteUserType(UserTypeRepository userTypeRepository, UserRepository userRepository) {
        return DeleteUserTypeUseCase.create(userTypeRepository, userRepository);
    }
}
