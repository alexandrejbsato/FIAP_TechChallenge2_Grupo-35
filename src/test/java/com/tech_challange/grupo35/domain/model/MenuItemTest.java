package com.tech_challange.grupo35.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuItemTest {

    private static final Restaurant RESTAURANT =
            Restaurant.reconstitute(UUID.randomUUID(), "Resto", null, "Italiana", "09-18", null);

    private MenuItem newItem() {
        return MenuItem.create("Lasanha", "Massa fresca", BigDecimal.valueOf(49.90), true, "/foto.jpg", RESTAURANT);
    }

    @Test
    void createBuildsItemWithoutId() {
        MenuItem item = newItem();

        assertNull(item.getId());
        assertEquals("Lasanha", item.getName());
        assertEquals("Massa fresca", item.getDescription());
        assertEquals(BigDecimal.valueOf(49.90), item.getPrice());
        assertEquals(true, item.getAvailableOnlyInRestaurant());
        assertEquals("/foto.jpg", item.getPhotoPath());
        assertSame(RESTAURANT, item.getRestaurant());
    }

    @Test
    void createRejectsBlankName() {
        assertThrows(IllegalArgumentException.class,
                () -> MenuItem.create(" ", "desc", BigDecimal.TEN, true, "/foto.jpg", RESTAURANT));
    }

    @Test
    void createRejectsBlankDescription() {
        assertThrows(IllegalArgumentException.class,
                () -> MenuItem.create("Nome", " ", BigDecimal.TEN, true, "/foto.jpg", RESTAURANT));
    }

    @Test
    void createRejectsBlankPhotoPath() {
        assertThrows(IllegalArgumentException.class,
                () -> MenuItem.create("Nome", "desc", BigDecimal.TEN, true, " ", RESTAURANT));
    }

    @Test
    void createRejectsNullAvailability() {
        assertThrows(IllegalArgumentException.class,
                () -> MenuItem.create("Nome", "desc", BigDecimal.TEN, null, "/foto.jpg", RESTAURANT));
    }

    @Test
    void createRejectsNonPositivePrice() {
        assertThrows(IllegalArgumentException.class,
                () -> MenuItem.create("Nome", "desc", BigDecimal.ZERO, true, "/foto.jpg", RESTAURANT));
        assertThrows(IllegalArgumentException.class,
                () -> MenuItem.create("Nome", "desc", null, true, "/foto.jpg", RESTAURANT));
    }

    @Test
    void createRejectsNullRestaurant() {
        assertThrows(IllegalArgumentException.class,
                () -> MenuItem.create("Nome", "desc", BigDecimal.TEN, true, "/foto.jpg", null));
    }

    @Test
    void reconstituteDoesNotValidate() {
        UUID id = UUID.randomUUID();

        MenuItem item = MenuItem.reconstitute(id, null, null, null, null, null, null);

        assertEquals(id, item.getId());
        assertNull(item.getName());
        assertNull(item.getRestaurant());
    }

    @Test
    void updateDetailsReplacesFieldsButKeepsRestaurant() {
        MenuItem item = newItem();

        item.updateDetails("Novo", "Nova desc", BigDecimal.valueOf(59.90), false, "/nova.jpg");

        assertEquals("Novo", item.getName());
        assertEquals("Nova desc", item.getDescription());
        assertEquals(BigDecimal.valueOf(59.90), item.getPrice());
        assertEquals(false, item.getAvailableOnlyInRestaurant());
        assertEquals("/nova.jpg", item.getPhotoPath());
        assertSame(RESTAURANT, item.getRestaurant());
    }

    @Test
    void updateDetailsRejectsInvalidValues() {
        MenuItem item = newItem();

        assertThrows(IllegalArgumentException.class,
                () -> item.updateDetails(" ", "desc", BigDecimal.TEN, true, "/foto.jpg"));
        assertThrows(IllegalArgumentException.class,
                () -> item.updateDetails("Nome", "desc", BigDecimal.ZERO, true, "/foto.jpg"));
    }
}
