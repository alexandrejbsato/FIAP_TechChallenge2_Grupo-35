package com.tech_challange.grupo35.domain.model;

import java.util.UUID;
import lombok.Data;

@Data
public class Restaurant {

    private UUID id;
    private String name;
    private Address address;
    private String cuisineType;
    private String openingHours;
    private User owner;
}
