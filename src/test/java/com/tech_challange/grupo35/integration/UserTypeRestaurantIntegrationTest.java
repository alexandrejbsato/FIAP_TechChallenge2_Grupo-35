package com.tech_challange.grupo35.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTypeRestaurantIntegrationTest {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

    @Test
    void fullFlow_createUserType_createUser_assignType_createRestaurant() {
        // create user type
        Map<String, String> userTypeReq = Map.of("name", "RESTAURANT_OWNER");
        ResponseEntity<Map> utResp = restTemplate.postForEntity(url("/api/v1/user-types"), userTypeReq, Map.class);
        assertThat(utResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String userTypeId = (String) ((Map) utResp.getBody()).get("id");

        // create user
        Map<String, String> userReq = new HashMap<>();
        userReq.put("name", "Owner One");
        userReq.put("email", "owner1@example.com");
        userReq.put("login", "owner1");
        userReq.put("password", "pass123");
        userReq.put("address", "Rua X, 1");
        userReq.put("cpf", "00000000000");

        ResponseEntity<Map> userResp = restTemplate.postForEntity(url("/api/v1/users"), userReq, Map.class);
        assertThat(userResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String userId = (String) ((Map) userResp.getBody()).get("id");

        // login to get token
        Map<String, String> loginReq = Map.of("login", "owner1", "password", "pass123");
        ResponseEntity<Map> loginResp = restTemplate.postForEntity(url("/api/v1/users/login"), loginReq, Map.class);
        assertThat(loginResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = (String) ((Map) loginResp.getBody()).get("token");
        assertThat(token).isNotBlank();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // assign user type
        Map<String, String> assignBody = Map.of("userTypeId", userTypeId);
        HttpEntity<Map<String, String>> assignEntity = new HttpEntity<>(assignBody, headers);
        ResponseEntity<Map> assignResp = restTemplate.exchange(url("/api/v1/users/" + userId + "/user-type"), HttpMethod.PATCH, assignEntity, Map.class);
        assertThat(assignResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((Map) assignResp.getBody()).get("userTypeId")).isEqualTo(userTypeId);

        // create restaurant
        Map<String, Object> address = new HashMap<>();
        address.put("street", "Rua A");
        address.put("number", "1");
        address.put("neighborhood", "Centro");
        address.put("city", "Cidade");
        address.put("state", "ST");
        address.put("zipCode", "00000-000");

        Map<String, Object> restoReq = new HashMap<>();
        restoReq.put("name", "Restaurante Teste");
        restoReq.put("address", address);
        restoReq.put("cuisineType", "Italiana");
        restoReq.put("openingHours", "09-18");
        restoReq.put("ownerId", userId);

        HttpEntity<Map<String, Object>> restoEntity = new HttpEntity<>(restoReq, headers);
        ResponseEntity<Map> restoResp = restTemplate.postForEntity(url("/api/v1/restaurants"), restoEntity, Map.class);
        assertThat(restoResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Map restoBody = restoResp.getBody();
        assertThat(restoBody).isNotNull();
        Map owner = (Map) restoBody.get("owner");
        assertThat(owner).isNotNull();
        assertThat(owner.get("id")).isEqualTo(userId);

        // fetch restaurant
        String restoId = String.valueOf(restoBody.get("id"));
        HttpEntity<Void> getEntity = new HttpEntity<>(headers);
        ResponseEntity<Map> getResp = restTemplate.exchange(url("/api/v1/restaurants/" + restoId), HttpMethod.GET, getEntity, Map.class);
        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((Map) getResp.getBody()).get("name")).isEqualTo("Restaurante Teste");
    }
}
