package com.tech_challange.grupo35.infrastructure.web.controller;

import com.tech_challange.grupo35.application.dto.CreateMenuItemRequest;
import com.tech_challange.grupo35.application.dto.MenuItemResponse;
import com.tech_challange.grupo35.application.dto.UpdateMenuItemRequest;
import com.tech_challange.grupo35.application.port.in.CreateMenuItem;
import com.tech_challange.grupo35.application.port.in.DeleteMenuItem;
import com.tech_challange.grupo35.application.port.in.GetMenuItemById;
import com.tech_challange.grupo35.application.port.in.GetMenuItemsByRestaurant;
import com.tech_challange.grupo35.application.port.in.UpdateMenuItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/restaurants/{restaurantId}/menu-items")
@RequiredArgsConstructor
@Tag(name = "Itens do Cardapio", description = "Endpoints para gerenciar itens do cardapio de um restaurante")
public class MenuItemController {

    private final CreateMenuItem createMenuItemUseCase;
    private final GetMenuItemsByRestaurant getMenuItemsByRestaurantUseCase;
    private final GetMenuItemById getMenuItemByIdUseCase;
    private final UpdateMenuItem updateMenuItemUseCase;
    private final DeleteMenuItem deleteMenuItemUseCase;

    private static final String MENU_ITEM_EXAMPLE = """
            {
              "id": "f1e2d3c4-b5a6-7890-1234-567890abcdef",
              "name": "Lasanha Bolonhesa",
              "description": "Massa fresca com molho bolonhesa e queijo gratinado",
              "price": 49.90,
              "availableOnlyInRestaurant": true,
              "photoPath": "/images/menu/lasanha-bolonhesa.jpg",
              "restaurantId": "a1b2c3d4-e5f6-7890-abcd-ef0123456789"
            }
            """;

    private static final String REQUEST_EXAMPLE = """
            {
              "name": "Lasanha Bolonhesa",
              "description": "Massa fresca com molho bolonhesa e queijo gratinado",
              "price": 49.90,
              "availableOnlyInRestaurant": true,
              "photoPath": "/images/menu/lasanha-bolonhesa.jpg"
            }
            """;

    @PostMapping
    @Operation(summary = "Criar item do cardapio", description = "Cria um novo item no cardapio do restaurante informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuItemResponse.class),
                            examples = @ExampleObject(value = MENU_ITEM_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada invalidos")
    })
    public ResponseEntity<MenuItemResponse> create(
            @PathVariable UUID restaurantId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = REQUEST_EXAMPLE)))
            @RequestBody @Valid CreateMenuItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createMenuItemUseCase.execute(restaurantId, request));
    }

    @GetMapping
    @Operation(summary = "Listar itens do cardapio", description = "Retorna os itens do cardapio do restaurante informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "[" + MENU_ITEM_EXAMPLE + "]"))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado")
    })
    public ResponseEntity<List<MenuItemResponse>> findByRestaurant(@PathVariable UUID restaurantId) {
        return ResponseEntity.ok(getMenuItemsByRestaurantUseCase.execute(restaurantId));
    }

    @GetMapping("/{menuItemId}")
    @Operation(summary = "Buscar item do cardapio por ID", description = "Retorna um item especifico do cardapio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuItemResponse.class),
                            examples = @ExampleObject(value = MENU_ITEM_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Restaurante ou item nao encontrado")
    })
    public ResponseEntity<MenuItemResponse> findById(@PathVariable UUID restaurantId, @PathVariable UUID menuItemId) {
        return ResponseEntity.ok(getMenuItemByIdUseCase.execute(restaurantId, menuItemId));
    }

    @PutMapping("/{menuItemId}")
    @Operation(summary = "Atualizar item do cardapio", description = "Atualiza um item do cardapio do restaurante informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuItemResponse.class),
                            examples = @ExampleObject(value = MENU_ITEM_EXAMPLE))),
            @ApiResponse(responseCode = "404", description = "Restaurante ou item nao encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada invalidos")
    })
    public ResponseEntity<MenuItemResponse> update(
            @PathVariable UUID restaurantId,
            @PathVariable UUID menuItemId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = REQUEST_EXAMPLE)))
            @RequestBody @Valid UpdateMenuItemRequest request) {
        return ResponseEntity.ok(updateMenuItemUseCase.execute(restaurantId, menuItemId, request));
    }

    @DeleteMapping("/{menuItemId}")
    @Operation(summary = "Deletar item do cardapio", description = "Remove um item do cardapio do restaurante informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou item nao encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID restaurantId, @PathVariable UUID menuItemId) {
        deleteMenuItemUseCase.execute(restaurantId, menuItemId);
        return ResponseEntity.noContent().build();
    }
}
