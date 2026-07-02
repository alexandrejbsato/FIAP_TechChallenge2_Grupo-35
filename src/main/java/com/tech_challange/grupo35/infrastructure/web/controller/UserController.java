package com.tech_challange.grupo35.infrastructure.web.controller;

import com.tech_challange.grupo35.application.dto.AssignUserTypeRequest;
import com.tech_challange.grupo35.application.dto.ChangePasswordRequest;
import com.tech_challange.grupo35.application.dto.CreateUserRequest;
import com.tech_challange.grupo35.application.dto.LoginRequest;
import com.tech_challange.grupo35.application.dto.LoginResponse;
import com.tech_challange.grupo35.application.dto.UpdateUserRequest;
import com.tech_challange.grupo35.application.dto.UserResponse;
import com.tech_challange.grupo35.application.port.in.AssignUserType;
import com.tech_challange.grupo35.application.port.in.ChangePassword;
import com.tech_challange.grupo35.application.port.in.CreateUser;
import com.tech_challange.grupo35.application.port.in.DeleteUser;
import com.tech_challange.grupo35.application.port.in.FindUsersByName;
import com.tech_challange.grupo35.application.port.in.LoginUser;
import com.tech_challange.grupo35.application.port.in.UpdateUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Endpoints para operações gerais de usuários")
public class UserController {

    private final CreateUser createUserUseCase;
    private final UpdateUser updateUserUseCase;
    private final ChangePassword changePasswordUseCase;
    private final DeleteUser deleteUserUseCase;
    private final FindUsersByName findUsersByNameUseCase;
    private final LoginUser loginUseCase;
    private final AssignUserType assignUserTypeUseCase;

    @PostMapping
    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário com os detalhes fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class),
                    examples = @ExampleObject(value = """
                        {
                          "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                          "name": "João Silva",
                          "email": "joao.silva@email.com",
                          "login": "joaosilva",
                          "address": "Rua das Flores, 100, São Paulo - SP",
                          "lastUpdatedAt": "2024-01-15T10:30:00",
                          "cpf": "123.456.789-00",
                          "userTypeId": null,
                          "userTypeName": null
                        }
                        """))),
            @ApiResponse(responseCode = "409", description = "E-mail, login ou CPF já cadastrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Email Já Cadastrado",
                          "status": 409,
                          "detail": "O email joao.silva@email.com já está em uso."
                        }
                        """))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Dados de Entrada Inválidos",
                          "status": 400,
                          "detail": "Um ou mais campos possuem valores inválidos.",
                          "erros": [
                            { "campo": "email", "mensagem": "must not be blank" }
                          ]
                        }
                        """)))
    })
    public ResponseEntity<UserResponse> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        {
                          "name": "João Silva",
                          "email": "joao.silva@email.com",
                          "login": "joaosilva",
                          "password": "senha123",
                          "address": "Rua das Flores, 100, São Paulo - SP",
                          "cpf": "123.456.789-00"
                        }
                        """)))
            @RequestBody @Valid CreateUserRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(createUserUseCase.execute(request));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar dados do usuário", description = "Atualiza os dados de um usuário existente. Todos os campos são opcionais.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class),
                    examples = @ExampleObject(value = """
                        {
                          "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                          "name": "João Silva Atualizado",
                          "email": "joao.silva@email.com",
                          "login": "joaosilva",
                          "address": "Av. Paulista, 1000, São Paulo - SP",
                          "lastUpdatedAt": "2024-01-15T11:00:00",
                          "cpf": "123.456.789-00",
                          "userTypeId": null,
                          "userTypeName": null
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Usuário Não Encontrado",
                          "status": 404,
                          "detail": "Usuário com ID a1b2c3d4-e5f6-7890-abcd-ef1234567890 não encontrado."
                        }
                        """))),
            @ApiResponse(responseCode = "409", description = "E-mail, login ou CPF já cadastrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Email Já Cadastrado",
                          "status": 409,
                          "detail": "O email joao.silva@email.com já está em uso."
                        }
                        """))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Dados de Entrada Inválidos",
                          "status": 400,
                          "detail": "Um ou mais campos possuem valores inválidos.",
                          "erros": [
                            { "campo": "email", "mensagem": "must be a well-formed email address" }
                          ]
                        }
                        """)))
    })
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        {
                          "name": "João Silva Atualizado",
                          "address": "Av. Paulista, 1000, São Paulo - SP"
                        }
                        """)))
            @RequestBody @Valid UpdateUserRequest request) {

        return ResponseEntity.ok(updateUserUseCase.execute(id, request));
    }

    @PatchMapping("/{id}/password")
    @Operation(summary = "Alterar senha do usuário", description = "Altera a senha de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Usuário Não Encontrado",
                          "status": 404,
                          "detail": "Usuário com ID a1b2c3d4-e5f6-7890-abcd-ef1234567890 não encontrado."
                        }
                        """))),
            @ApiResponse(responseCode = "400", description = "Senha atual inválida ou dados de entrada inválidos",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Senha Inválida",
                          "status": 400,
                          "detail": "A senha atual fornecida está incorreta."
                        }
                        """)))
    })
    public ResponseEntity<Void> changePassword(
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        {
                          "currentPassword": "senha123",
                          "newPassword": "novaSenha456"
                        }
                        """)))
            @RequestBody @Valid ChangePasswordRequest request) {

        changePasswordUseCase.execute(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Usuário Não Encontrado",
                          "status": 404,
                          "detail": "Usuário com ID a1b2c3d4-e5f6-7890-abcd-ef1234567890 não encontrado."
                        }
                        """)))
    })
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        deleteUserUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Buscar usuários por nome", description = "Retorna usuários cujo nome contenha o valor informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        [
                          {
                            "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                            "name": "João Silva",
                            "email": "joao.silva@email.com",
                            "login": "joaosilva",
                            "address": "Rua das Flores, 100, São Paulo - SP",
                            "lastUpdatedAt": "2024-01-15T10:30:00",
                            "cpf": "123.456.789-00",
                            "userTypeId": null,
                            "userTypeName": null
                          }
                        ]
                        """)))
    })
    public ResponseEntity<List<UserResponse>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(findUsersByNameUseCase.execute(name));
    }

    @PatchMapping("/{id}/user-type")
    @Operation(summary = "Associar tipo de usuário", description = "Associa um tipo de usuário a um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo associado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class),
                    examples = @ExampleObject(value = """
                        {
                          "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                          "name": "João Silva",
                          "userTypeId": "c3d4e5f6-a7b8-9012-cdef-012345678901",
                          "userTypeName": "Cliente"
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Usuário ou tipo de usuário não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Usuário Não Encontrado",
                          "status": 404,
                          "detail": "Usuário com ID a1b2c3d4-e5f6-7890-abcd-ef1234567890 não encontrado."
                        }
                        """)))
    })
    public ResponseEntity<UserResponse> assignUserType(
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        { "userTypeId": "c3d4e5f6-a7b8-9012-cdef-012345678901" }
                        """)))
            @RequestBody @Valid AssignUserTypeRequest request) {
        return ResponseEntity.ok(assignUserTypeUseCase.execute(id, request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Valida login e senha do usuário e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class),
                    examples = @ExampleObject(value = """
                        {
                          "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
                        }
                        """))),
            @ApiResponse(responseCode = "400", description = "Login ou senha inválidos",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Senha Inválida",
                          "status": 400,
                          "detail": "A senha atual fornecida está incorreta."
                        }
                        """))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Usuário Não Encontrado",
                          "status": 404,
                          "detail": "Usuário não encontrado."
                        }
                        """)))
    })
    public ResponseEntity<LoginResponse> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        {
                          "login": "joaosilva",
                          "password": "senha123"
                        }
                        """)))
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(loginUseCase.execute(request.login(), request.password()));
    }
}
