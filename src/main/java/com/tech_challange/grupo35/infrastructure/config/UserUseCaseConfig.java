package com.tech_challange.grupo35.infrastructure.config;

import com.tech_challange.grupo35.application.mapper.UserMapper;
import com.tech_challange.grupo35.application.port.in.AssignUserType;
import com.tech_challange.grupo35.application.port.in.ChangePassword;
import com.tech_challange.grupo35.application.port.in.CreateUser;
import com.tech_challange.grupo35.application.port.in.DeleteUser;
import com.tech_challange.grupo35.application.port.in.FindUsersByName;
import com.tech_challange.grupo35.application.port.in.LoginUser;
import com.tech_challange.grupo35.application.port.in.UpdateUser;
import com.tech_challange.grupo35.application.port.out.TokenService;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import com.tech_challange.grupo35.application.usecase.AssignUserTypeUseCase;
import com.tech_challange.grupo35.application.usecase.ChangePasswordUseCase;
import com.tech_challange.grupo35.application.usecase.CreateUserUseCase;
import com.tech_challange.grupo35.application.usecase.DeleteUserUseCase;
import com.tech_challange.grupo35.application.usecase.FindUsersByNameUseCase;
import com.tech_challange.grupo35.application.usecase.LoginUseCase;
import com.tech_challange.grupo35.application.usecase.UpdateUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserUseCaseConfig {

    @Bean
    public CreateUser createUser(UserRepository userRepository, UserMapper userMapper) {
        return CreateUserUseCase.create(userRepository, userMapper);
    }

    @Bean
    public UpdateUser updateUser(UserRepository userRepository, UserMapper userMapper) {
        return UpdateUserUseCase.create(userRepository, userMapper);
    }

    @Bean
    public DeleteUser deleteUser(UserRepository userRepository) {
        return DeleteUserUseCase.create(userRepository);
    }

    @Bean
    public FindUsersByName findUsersByName(UserRepository userRepository) {
        return FindUsersByNameUseCase.create(userRepository);
    }

    @Bean
    public ChangePassword changePassword(UserRepository userRepository) {
        return ChangePasswordUseCase.create(userRepository);
    }

    @Bean
    public LoginUser loginUser(UserRepository userRepository, TokenService tokenService) {
        return LoginUseCase.create(userRepository, tokenService);
    }

    @Bean
    public AssignUserType assignUserType(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        return AssignUserTypeUseCase.create(userRepository, userTypeRepository);
    }
}
