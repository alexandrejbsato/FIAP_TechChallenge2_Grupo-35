package com.tech_challange.grupo35.infrastructure.web.controller;

import com.tech_challange.grupo35.application.dto.CreateUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UpdateUserTypeRequest;
import com.tech_challange.grupo35.application.dto.UserTypeResponse;
import com.tech_challange.grupo35.application.port.in.CreateUserType;
import com.tech_challange.grupo35.application.port.in.DeleteUserType;
import com.tech_challange.grupo35.application.port.in.GetAllUserTypes;
import com.tech_challange.grupo35.application.port.in.GetUserTypeById;
import com.tech_challange.grupo35.application.port.in.GetUserTypeByName;
import com.tech_challange.grupo35.application.port.in.UpdateUserType;
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
@RequestMapping("/api/v1/user-types")
@RequiredArgsConstructor
@Tag(name = "Tipos de Usuário", description = "Endpoints para gerenciar tipos de usuário")
public class UserTypeController {

    private final CreateUserType createUserTypeUseCase;
    private final GetAllUserTypes getAllUserTypesUseCase;
    private final GetUserTypeById getUserTypeByIdUseCase;
    private final GetUserTypeByName getUserTypeByNameUseCase;
    private final UpdateUserType updateUserTypeUseCase;
    private final DeleteUserType deleteUserTypeUseCase;

    @PostMapping
    @Operation(summary = "Criar tipo de usuário", description = "Cria um novo tipo de usuário com um nome único")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo de usuário criado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserTypeResponse.class),
                    examples = @ExampleObject(value = """
                        {
                          "id": "c3d4e5f6-a7b8-9012-cdef-012345678901",
                          "name": "Cliente"
                        }
                        """))),
            @ApiResponse(responseCode = "409", description = "Nome de tipo já cadastrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Tipo de Usuário Já Cadastrado",
                          "status": 409,
                          "detail": "Tipo de usuário já cadastrado com o nome: Cliente"
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
                            { "campo": "name", "mensagem": "must not be blank" }
                          ]
                        }
                        """)))
    })
    public ResponseEntity<UserTypeResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        { "name": "Cliente" }
                        """)))
            @RequestBody @Valid CreateUserTypeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserTypeUseCase.execute(request));
    }

    @GetMapping
    @Operation(summary = "Listar todos os tipos de usuário", description = "Retorna a lista completa de tipos de usuário cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        [
                          { "id": "c3d4e5f6-a7b8-9012-cdef-012345678901", "name": "CUSTOMER" },
                          { "id": "d4e5f6a7-b8c9-0123-defa-123456789012", "name": "RESTAURANT_OWNER" }
                        ]
                        """)))
    })
    public ResponseEntity<List<UserTypeResponse>> findAll() {
        return ResponseEntity.ok(getAllUserTypesUseCase.execute());
    }

    @GetMapping(params = "name")
    @Operation(summary = "Buscar tipo de usuário por nome", description = "Retorna um tipo de usuário específico pelo seu nome (único)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserTypeResponse.class),
                    examples = @ExampleObject(value = """
                        { "id": "c3d4e5f6-a7b8-9012-cdef-012345678901", "name": "Cliente" }
                        """))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Tipo de Usuário Não Encontrado",
                          "status": 404,
                          "detail": "Tipo de usuário não encontrado com nome: Cliente"
                        }
                        """)))
    })
    public ResponseEntity<UserTypeResponse> findByName(@RequestParam String name) {
        return ResponseEntity.ok(getUserTypeByNameUseCase.execute(name));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar tipo de usuário por ID", description = "Retorna um tipo de usuário específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserTypeResponse.class),
                    examples = @ExampleObject(value = """
                        { "id": "c3d4e5f6-a7b8-9012-cdef-012345678901", "name": "Cliente" }
                        """))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Tipo de Usuário Não Encontrado",
                          "status": 404,
                          "detail": "Tipo de usuário não encontrado com id: c3d4e5f6-a7b8-9012-cdef-012345678901"
                        }
                        """)))
    })
    public ResponseEntity<UserTypeResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(getUserTypeByIdUseCase.execute(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar tipo de usuário", description = "Atualiza o nome de um tipo de usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserTypeResponse.class),
                    examples = @ExampleObject(value = """
                        { "id": "c3d4e5f6-a7b8-9012-cdef-012345678901", "name": "Cliente Premium" }
                        """))),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Tipo de Usuário Não Encontrado",
                          "status": 404,
                          "detail": "Tipo de usuário não encontrado com id: c3d4e5f6-a7b8-9012-cdef-012345678901"
                        }
                        """))),
            @ApiResponse(responseCode = "409", description = "Nome de tipo já cadastrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Tipo de Usuário Já Cadastrado",
                          "status": 409,
                          "detail": "Tipo de usuário já cadastrado com o nome: Cliente Premium"
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
                            { "campo": "name", "mensagem": "must not be blank" }
                          ]
                        }
                        """)))
    })
    public ResponseEntity<UserTypeResponse> update(
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = """
                        { "name": "Cliente Premium" }
                        """)))
            @RequestBody @Valid UpdateUserTypeRequest request) {
        return ResponseEntity.ok(updateUserTypeUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar tipo de usuário", description = "Remove um tipo de usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tipo de usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Tipo de Usuário Não Encontrado",
                          "status": 404,
                          "detail": "Tipo de usuário não encontrado com id: c3d4e5f6-a7b8-9012-cdef-012345678901"
                        }
                        """)))
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteUserTypeUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
