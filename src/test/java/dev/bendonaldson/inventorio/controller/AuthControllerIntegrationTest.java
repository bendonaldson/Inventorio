package dev.bendonaldson.inventorio.controller;

import dev.bendonaldson.inventorio.dto.AuthResponseDto;
import dev.bendonaldson.inventorio.dto.LoginRequestDto;
import dev.bendonaldson.inventorio.dto.RegisterRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the authentication and registration endpoints.
 */
class AuthControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldRegisterUserAndThenLoginSuccessfully() {
        // Arrange: Registration
        var registerRequest = new RegisterRequestDto("testuser", "password123");

        // Act: Register
        ResponseEntity<AuthResponseDto> registerResponse = restTemplate.postForEntity(
                "/api/auth/register", registerRequest, AuthResponseDto.class);

        // Assert: Registration
        assertEquals(HttpStatus.OK, registerResponse.getStatusCode());
        assertNotNull(registerResponse.getBody());
        assertNotNull(registerResponse.getBody().accessToken());

        // Arrange: Login
        var loginRequest = new LoginRequestDto("testuser", "password123");

        // Act: Login
        ResponseEntity<AuthResponseDto> loginResponse = restTemplate.postForEntity(
                "/api/auth/login", loginRequest, AuthResponseDto.class);

        // Assert: Login
        assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
        assertNotNull(loginResponse.getBody());
        assertNotNull(loginResponse.getBody().accessToken());
    }

    @Test
    void shouldFailRegistrationWhenUsernameIsTaken() {
        // Arrange
        var registerRequest = new RegisterRequestDto("duplicateuser", "password123");
        // First registration should succeed
        restTemplate.postForEntity("/api/auth/register", registerRequest, AuthResponseDto.class);

        // Act: Try to register the same user again
        ResponseEntity<String> errorResponse = restTemplate.postForEntity(
                "/api/auth/register", registerRequest, String.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, errorResponse.getStatusCode());
        assertTrue(errorResponse.getBody().contains("is already taken"));
    }
}