package com.tech_challange.grupo35.application.port.out;

import com.tech_challange.grupo35.domain.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    boolean existsByCpf(String cpf);
    boolean existsById(UUID id);
    boolean existsByUserTypeId(UUID userTypeId);
    User save(User user);
    List<User> findByNameContainingIgnoreCase(String name);
    Optional<User> findByLogin(String login);
    void deleteById(UUID id);
}
