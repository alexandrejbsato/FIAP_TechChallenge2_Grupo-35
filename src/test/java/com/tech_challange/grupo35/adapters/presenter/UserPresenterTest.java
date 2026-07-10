package com.tech_challange.grupo35.adapters.presenter;

import com.tech_challange.grupo35.application.dto.UserResponse;
import com.tech_challange.grupo35.application.mapper.AddressMapper;
import com.tech_challange.grupo35.domain.model.Address;
import com.tech_challange.grupo35.domain.model.User;
import com.tech_challange.grupo35.domain.model.UserType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserPresenterTest {

    private final UserPresenter presenter = new UserPresenter(new AddressMapper());

    private User user(UUID id, String name, UserType userType) {
        Address address = Address.create("Rua X", "10", "Centro", "Cidade", "ST", "01000-000");
        return User.reconstitute(id, name, name + "@mail.com", name, "secret",
                address, "123.456.789-00", LocalDateTime.now(), userType);
    }

    @Test
    void toResponseMapsAllFieldsIncludingUserTypeAndAddress() {
        UUID id = UUID.randomUUID();
        UserType userType = UserType.reconstitute(UUID.randomUUID(), "CUSTOMER");
        UserResponse response = presenter.toResponse(user(id, "joao", userType));

        assertEquals(id, response.id());
        assertEquals("joao", response.name());
        assertEquals("joao@mail.com", response.email());
        assertEquals("joao", response.login());
        assertEquals("Rua X", response.address().street());
        assertEquals("123.456.789-00", response.cpf());
        assertEquals(userType.getId(), response.userTypeId());
        assertEquals("CUSTOMER", response.userTypeName());
    }

    @Test
    void toResponseHandlesNullUserType() {
        UserResponse response = presenter.toResponse(user(UUID.randomUUID(), "maria", null));

        assertNull(response.userTypeId());
        assertNull(response.userTypeName());
    }

    @Test
    void toResponseListMapsEveryElement() {
        List<UserResponse> responses = presenter.toResponseList(List.of(
                user(UUID.randomUUID(), "a", null),
                user(UUID.randomUUID(), "b", null)));

        assertEquals(2, responses.size());
        assertEquals("a", responses.get(0).name());
        assertEquals("b", responses.get(1).name());
    }
}
