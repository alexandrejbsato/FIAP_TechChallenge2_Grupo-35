package com.tech_challange.grupo35.infrastructure.web.controller;

import com.tech_challange.grupo35.application.dto.CreateRestaurantRequest;
import com.tech_challange.grupo35.application.dto.RestaurantResponse;
import com.tech_challange.grupo35.application.dto.UpdateRestaurantRequest;
import com.tech_challange.grupo35.application.port.in.CreateRestaurant;
import com.tech_challange.grupo35.application.port.in.DeleteRestaurant;
import com.tech_challange.grupo35.application.port.in.GetAllRestaurants;
import com.tech_challange.grupo35.application.port.in.GetRestaurantById;
import com.tech_challange.grupo35.application.port.in.UpdateRestaurant;
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
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurantes", description = "Endpoints para gerenciar restaurantes")
public class RestaurantController {

    private final CreateRestaurant createRestaurantUseCase;
    private final GetAllRestaurants getAllRestaurantsUseCase;
    private final GetRestaurantById getRestaurantByIdUseCase;
    private final UpdateRestaurant updateRestaurantUseCase;
    private final DeleteRestaurant deleteRestaurantUseCase;

    private static final String RESTAURANT_EXAMPLE = """
            {
              "id": "f1e2d3c4-b5a6-7890-1234-567890abcdef",
              "name": "Cantina da Nonna",
              "address": {
                "street": "Rua das Flores",
                "number": "100",
                "neighborhood": "Centro",
                "city": "São Paulo",
                "state": "SP",
                "zipCode": "01000-000"
              },
              "cuisineType": "Italiana",
              "openingHours": "Seg-Sex 11:00-23:00",
              "owner": {
                "id": "a1b2c3d4-e5f6-7890-abcd-ef0123456789",
                "name": "Mario Rossi",
                "userType": "RESTAURANT_OWNER"
              }
            }
            """;

    private static final String REQUEST_EXAMPLE = """
            {
              "name": "Cantina da Nonna",
              "address": {
                "street": "Rua das Flores",
                "number": "100",
                "neighborhood": "Centro",
                "city": "São Paulo",
                "state": "SP",
                "zipCode": "01000-000"
              },
              "cuisineType": "Italiana",
              "openingHours": "Seg-Sex 11:00-23:00",
              "ownerId": "a1b2c3d4-e5f6-7890-abcd-ef0123456789"
            }
            """;

    @PostMapping
    @Operation(summary = "Criar restaurante",
            description = "Cria um novo restaurante. O dono informado deve existir e ser do tipo RESTAURANT_OWNER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantResponse.class),
                    examples = @ExampleObject(value = RESTAURANT_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Usuário dono não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Usuário Não Encontrado",
                          "status": 404,
                          "detail": "Usuário não encontrado com id: a1b2c3d4-e5f6-7890-abcd-ef0123456789"
                        }
                        """))),
            @ApiResponse(responseCode = "409", description = "Usuário não é do tipo RESTAURANT_OWNER",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Dono de Restaurante Inválido",
                          "status": 409,
                          "detail": "O usuário informado como dono deve ser do tipo RESTAURANT_OWNER: a1b2c3d4-e5f6-7890-abcd-ef0123456789"
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
    public ResponseEntity<RestaurantResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = REQUEST_EXAMPLE)))
            @RequestBody @Valid CreateRestaurantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createRestaurantUseCase.execute(request));
    }

    @GetMapping
    @Operation(summary = "Listar restaurantes", description = "Retorna a lista completa de restaurantes cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = "[" + RESTAURANT_EXAMPLE + "]")))
    })
    public ResponseEntity<List<RestaurantResponse>> findAll() {
        return ResponseEntity.ok(getAllRestaurantsUseCase.execute());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID", description = "Retorna um restaurante específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantResponse.class),
                    examples = @ExampleObject(value = RESTAURANT_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Restaurante Não Encontrado",
                          "status": 404,
                          "detail": "Restaurante não encontrado com id: f1e2d3c4-b5a6-7890-1234-567890abcdef"
                        }
                        """)))
    })
    public ResponseEntity<RestaurantResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(getRestaurantByIdUseCase.execute(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar restaurante",
            description = "Atualiza um restaurante existente. O dono informado deve existir e ser do tipo RESTAURANT_OWNER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = RestaurantResponse.class),
                    examples = @ExampleObject(value = RESTAURANT_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuário dono não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Restaurante Não Encontrado",
                          "status": 404,
                          "detail": "Restaurante não encontrado com id: f1e2d3c4-b5a6-7890-1234-567890abcdef"
                        }
                        """))),
            @ApiResponse(responseCode = "409", description = "Usuário não é do tipo RESTAURANT_OWNER",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Dono de Restaurante Inválido",
                          "status": 409,
                          "detail": "O usuário informado como dono deve ser do tipo RESTAURANT_OWNER: a1b2c3d4-e5f6-7890-abcd-ef0123456789"
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
    public ResponseEntity<RestaurantResponse> update(
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(value = REQUEST_EXAMPLE)))
            @RequestBody @Valid UpdateRestaurantRequest request) {
        return ResponseEntity.ok(updateRestaurantUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar restaurante", description = "Remove um restaurante pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurante deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
                content = @Content(mediaType = "application/problem+json",
                    examples = @ExampleObject(value = """
                        {
                          "type": "about:blank",
                          "title": "Restaurante Não Encontrado",
                          "status": 404,
                          "detail": "Restaurante não encontrado com id: f1e2d3c4-b5a6-7890-1234-567890abcdef"
                        }
                        """)))
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteRestaurantUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
