package com.tech_challange.grupo35.infrastructure.persistence.repository;

import com.tech_challange.grupo35.domain.model.UserType;
import com.tech_challange.grupo35.application.port.out.UserTypeRepository;
import com.tech_challange.grupo35.infrastructure.persistence.jpa.UserTypeJpaRepository;
import com.tech_challange.grupo35.infrastructure.persistence.mapper.UserTypeEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserTypeRepositoryImpl implements UserTypeRepository {

    private final UserTypeJpaRepository jpaRepository;
    private final UserTypeEntityMapper mapper;

    @Override
    public UserType save(UserType userType) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(userType)));
    }

    @Override
    public Optional<UserType> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<UserType> findByName(String name) {
        return jpaRepository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public List<UserType> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }
}
