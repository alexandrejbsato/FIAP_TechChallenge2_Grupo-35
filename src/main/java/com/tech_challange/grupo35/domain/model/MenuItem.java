package com.tech_challange.grupo35.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;

@Getter
public class MenuItem {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availableOnlyInRestaurant;
    private String photoPath;
    private Restaurant restaurant;

    private MenuItem(UUID id, String name, String description, BigDecimal price,
                     Boolean availableOnlyInRestaurant, String photoPath, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnlyInRestaurant = availableOnlyInRestaurant;
        this.photoPath = photoPath;
        this.restaurant = restaurant;
    }

    /**
     * Cria um item de cardápio novo, validando as invariantes. Um item sempre pertence a
     * um restaurante — não existe item válido sem restaurante. O id é gerado pela persistência.
     */
    public static MenuItem create(String name, String description, BigDecimal price,
                                  Boolean availableOnlyInRestaurant, String photoPath, Restaurant restaurant) {
        requireNotBlank(name, "name");
        requireNotBlank(description, "description");
        requirePositive(price, "price");
        requireNotNull(availableOnlyInRestaurant, "availableOnlyInRestaurant");
        requireNotBlank(photoPath, "photoPath");
        requireNotNull(restaurant, "restaurant");
        return new MenuItem(null, name, description, price, availableOnlyInRestaurant, photoPath, restaurant);
    }

    /**
     * Reconstitui um item já existente a partir da persistência, sem revalidar.
     */
    public static MenuItem reconstitute(UUID id, String name, String description, BigDecimal price,
                                        Boolean availableOnlyInRestaurant, String photoPath, Restaurant restaurant) {
        return new MenuItem(id, name, description, price, availableOnlyInRestaurant, photoPath, restaurant);
    }

    /**
     * Atualiza os dados do item. O restaurante ao qual o item pertence não muda.
     */
    public void updateDetails(String name, String description, BigDecimal price,
                              Boolean availableOnlyInRestaurant, String photoPath) {
        requireNotBlank(name, "name");
        requireNotBlank(description, "description");
        requirePositive(price, "price");
        requireNotNull(availableOnlyInRestaurant, "availableOnlyInRestaurant");
        requireNotBlank(photoPath, "photoPath");
        this.name = name;
        this.description = description;
        this.price = price;
        this.availableOnlyInRestaurant = availableOnlyInRestaurant;
        this.photoPath = photoPath;
    }

    private static void requireNotBlank(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(field + " is required");
        }
    }

    private static void requireNotNull(Object value, String field) {
        if (value == null) {
            throw new IllegalArgumentException(field + " is required");
        }
    }

    private static void requirePositive(BigDecimal value, String field) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(field + " must be positive");
        }
    }
}
