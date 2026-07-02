package com.tech_challange.grupo35.infrastructure.persistence.jpa;

import com.tech_challange.grupo35.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    boolean existsByCpf(String cpf);
    boolean existsByUserTypeId(UUID userTypeId);
    List<UserEntity> findByNameContainingIgnoreCase(String name);
    Optional<UserEntity> findByLogin(String login);
}
