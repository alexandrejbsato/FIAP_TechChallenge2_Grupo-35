package com.tech_challange.grupo35.domain.model;

import com.tech_challange.grupo35.domain.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private Address address() {
        return Address.create("Rua A", "10", "Centro", "Cidade", "ST", "00000-000");
    }

    private User userWith(String password, UserType userType) {
        return User.reconstitute(UUID.randomUUID(), "Name", "mail@mail.com", "login",
                password, address(), "12345678900", null, userType);
    }

    @Test
    void createStampsTimestampAndLeavesIdAndTypeNull() {
        User user = User.create("Name", "mail@mail.com", "login", "secret", address(), "12345678900");

        assertNull(user.getId());
        assertNull(user.getUserType());
        assertNotNull(user.getLastUpdatedAt());
        assertEquals("secret", user.getPassword());
    }

    @Test
    void createRejectsBlankRequiredField() {
        assertThrows(IllegalArgumentException.class,
                () -> User.create(" ", "mail@mail.com", "login", "secret", address(), "12345678900"));
    }

    @Test
    void isRestaurantOwnerTrueWhenTypeMatches() {
        User user = userWith("x", UserType.create(UserTypeNames.RESTAURANT_OWNER));

        assertTrue(user.isRestaurantOwner());
    }

    @Test
    void isRestaurantOwnerFalseWhenTypeIsNull() {
        assertFalse(userWith("x", null).isRestaurantOwner());
    }

    @Test
    void isRestaurantOwnerFalseWhenTypeIsCustomer() {
        User user = userWith("x", UserType.create(UserTypeNames.CUSTOMER));

        assertFalse(user.isRestaurantOwner());
    }

    @Test
    void changePasswordUpdatesWhenCurrentMatches() {
        User user = userWith("old", null);

        user.changePassword("old", "new");

        assertEquals("new", user.getPassword());
        assertNotNull(user.getLastUpdatedAt());
    }

    @Test
    void changePasswordThrowsWhenCurrentDoesNotMatch() {
        User user = userWith("old", null);

        assertThrows(InvalidPasswordException.class, () -> user.changePassword("wrong", "new"));
        assertEquals("old", user.getPassword());
    }

    @Test
    void passwordMatchesReflectsStoredPassword() {
        User user = userWith("secret", null);

        assertTrue(user.passwordMatches("secret"));
        assertFalse(user.passwordMatches("other"));
    }

    @Test
    void assignTypeSetsTypeAndStampsTimestamp() {
        User user = userWith("x", null);
        UserType owner = UserType.create(UserTypeNames.RESTAURANT_OWNER);

        user.assignType(owner);

        assertEquals(owner, user.getUserType());
        assertNotNull(user.getLastUpdatedAt());
    }

    @Test
    void updateProfileAppliesOnlyNonNullFields() {
        User user = userWith("x", null);

        user.updateProfile(null, "new@mail.com", "newlogin", null, "99999999999");

        assertEquals("Name", user.getName());
        assertEquals("new@mail.com", user.getEmail());
        assertEquals("newlogin", user.getLogin());
        assertEquals("99999999999", user.getCpf());
        assertNotNull(user.getLastUpdatedAt());
    }
}
