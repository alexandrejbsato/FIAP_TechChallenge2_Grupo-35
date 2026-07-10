package com.tech_challange.grupo35.application.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuItemRequestValidationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void rejectsInvalidCreateRequestFields() {
        CreateMenuItemRequest request = new CreateMenuItemRequest(
                "",
                "",
                BigDecimal.ZERO,
                null,
                ""
        );

        Set<ConstraintViolation<CreateMenuItemRequest>> violations = validator.validate(request);

        assertEquals(5, violations.size());
    }

    @Test
    void rejectsInvalidUpdateRequestFields() {
        UpdateMenuItemRequest request = new UpdateMenuItemRequest(
                "",
                "",
                BigDecimal.ZERO,
                null,
                ""
        );

        Set<ConstraintViolation<UpdateMenuItemRequest>> violations = validator.validate(request);

        assertEquals(5, violations.size());
    }
}
