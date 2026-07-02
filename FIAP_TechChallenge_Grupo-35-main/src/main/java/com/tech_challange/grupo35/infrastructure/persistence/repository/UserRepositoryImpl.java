package com.tech_challange.grupo35.infrastructure.persistence.repository;

import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.application.port.out.UserRepository;
import com.tech_challange.grupo35.infrastructure.persistence.jpa.UserJpaRepository;
import com.tech_challange.grupo35.infrastructure.persistence.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByLogin(String login) {
        return jpaRepository.existsByLogin(login);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }

    @Override
    public boolean existsByUserTypeId(UUID userTypeId) {
        return jpaRepository.existsByUserTypeId(userTypeId);
    }

    @Override
    @Transactional
    public User save(User user) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(user)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByNameContainingIgnoreCase(String name) {
        return jpaRepository.findByNameContainingIgnoreCase(name).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String login) {
        return jpaRepository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}
