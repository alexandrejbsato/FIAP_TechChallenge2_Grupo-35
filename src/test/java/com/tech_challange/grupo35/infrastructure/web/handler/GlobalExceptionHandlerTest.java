package com.tech_challange.grupo35.infrastructure.web.handler;

import com.tech_challange.grupo35.domain.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handlesVariousExceptions() {
        ProblemDetail pd;

        pd = handler.handleEmailAlreadyExists(new EmailAlreadyExistsException("a@x.com"));
                assertThat(pd.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(pd.getTitle()).isEqualTo("Email Já Cadastrado");
        assertThat(pd.getDetail()).isEqualTo(new EmailAlreadyExistsException("a@x.com").getMessage());

        pd = handler.handleLoginAlreadyExists(new LoginAlreadyExistsException("login"));
                assertThat(pd.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(pd.getTitle()).isEqualTo("Login Já Cadastrado");
        assertThat(pd.getDetail()).isEqualTo(new LoginAlreadyExistsException("login").getMessage());

        pd = handler.handleCpfAlreadyExists(new CpfAlreadyExistsException("00000000000"));
                assertThat(pd.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(pd.getTitle()).isEqualTo("CPF Já Cadastrado");
        assertThat(pd.getDetail()).isEqualTo(new CpfAlreadyExistsException("00000000000").getMessage());

        pd = handler.handleInvalidPassword(new InvalidPasswordException());
                assertThat(pd.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(pd.getTitle()).isEqualTo("Senha Inválida");
        assertThat(pd.getDetail()).isEqualTo("A senha atual fornecida está incorreta.");

        UUID uid = UUID.randomUUID();
        pd = handler.handleUserNotFound(new UserNotFoundException(uid));
                assertThat(pd.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(pd.getTitle()).isEqualTo("Usuário Não Encontrado");
        assertThat(pd.getDetail()).isEqualTo(new UserNotFoundException(uid).getMessage());

        UUID userTypeId = UUID.randomUUID();
        pd = handler.handleUserTypeNotFound(new UserTypeNotFoundException(userTypeId));
                assertThat(pd.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(pd.getTitle()).isEqualTo("Tipo de Usuário Não Encontrado");
        assertThat(pd.getDetail()).isEqualTo(new UserTypeNotFoundException(userTypeId).getMessage());

        pd = handler.handleUserTypeNameAlreadyExists(new UserTypeNameAlreadyExistsException("ADMIN"));
                assertThat(pd.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(pd.getTitle()).isEqualTo("Tipo de Usuário Já Cadastrado");
        assertThat(pd.getDetail()).isEqualTo(new UserTypeNameAlreadyExistsException("ADMIN").getMessage());

        UUID inUseId = UUID.randomUUID();
        pd = handler.handleUserTypeInUse(new UserTypeInUseException(inUseId));
                assertThat(pd.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(pd.getTitle()).isEqualTo("Tipo de Usuário em Uso");
        assertThat(pd.getDetail()).isEqualTo(new UserTypeInUseException(inUseId).getMessage());

        UUID restId = UUID.randomUUID();
        pd = handler.handleRestaurantNotFound(new RestaurantNotFoundException(restId));
                assertThat(pd.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(pd.getTitle()).isEqualTo("Restaurante Não Encontrado");
        assertThat(pd.getDetail()).isEqualTo(new RestaurantNotFoundException(restId).getMessage());

        UUID ownerId = UUID.randomUUID();
        pd = handler.handleInvalidRestaurantOwner(new InvalidRestaurantOwnerException(ownerId));
                assertThat(pd.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(pd.getTitle()).isEqualTo("Dono de Restaurante Inválido");
        assertThat(pd.getDetail()).isEqualTo(new InvalidRestaurantOwnerException(ownerId).getMessage());
    }
}
