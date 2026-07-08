package com.tech_challange.grupo35.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Data;

@Data
public class MenuItem {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availableOnlyInRestaurant;
    private String photoPath;
    private Restaurant restaurant;
}
