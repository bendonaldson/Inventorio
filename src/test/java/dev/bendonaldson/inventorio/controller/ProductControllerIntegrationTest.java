package dev.bendonaldson.inventorio.controller;

import dev.bendonaldson.inventorio.dto.AuthResponseDto;
import dev.bendonaldson.inventorio.dto.LoginRequestDto;
import dev.bendonaldson.inventorio.dto.ProductRequestDto;
import dev.bendonaldson.inventorio.dto.ProductResponseDto;
import dev.bendonaldson.inventorio.model.Role;
import dev.bendonaldson.inventorio.model.User;
import dev.bendonaldson.inventorio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for the ProductController endpoints.
 * Verifies CRUD operations and role-based access control.
 */
class ProductControllerIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private String adminToken;
    private String userToken;

    /**
     * Sets up the test environment before each test method.
     * Creates admin and user roles and logs them in to get JWTs.
     */
    @BeforeEach
    void setUp() {
        // Create an admin user directly in the database for testing
        userRepository.save(new User("admin", passwordEncoder.encode("password"), Role.ADMIN));
        // Create a regular user
        userRepository.save(new User("user", passwordEncoder.encode("password"), Role.USER));

        // Login as admin to get a token
        var adminLogin = new LoginRequestDto("admin", "password");
        ResponseEntity<AuthResponseDto> adminResponse = restTemplate.postForEntity("/api/auth/login", adminLogin, AuthResponseDto.class);
        assertNotNull(adminResponse.getBody(), "Admin login response body should not be null");
        adminToken = adminResponse.getBody().accessToken();

        // Login as user to get a token
        var userLogin = new LoginRequestDto("user", "password");
        ResponseEntity<AuthResponseDto> userResponse = restTemplate.postForEntity("/api/auth/login", userLogin, AuthResponseDto.class);
        assertNotNull(userResponse.getBody(), "User login response body should not be null");
        userToken = userResponse.getBody().accessToken();
    }

    @Test
    void adminShouldBeAbleToCreateProduct() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(adminToken);
        var requestDto = new ProductRequestDto("Admin Keyboard", "Mechanical", new BigDecimal("150.00"), 100, null);
        var requestEntity = new HttpEntity<>(requestDto, headers);

        // Act
        ResponseEntity<ProductResponseDto> response = restTemplate.postForEntity("/api/products", requestEntity, ProductResponseDto.class);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Admin Keyboard", response.getBody().name());
    }

    @Test
    void userShouldNotBeAbleToCreateProduct() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userToken);
        var requestDto = new ProductRequestDto("User Keyboard", "Should fail", new BigDecimal("100.00"), 50, null);
        var requestEntity = new HttpEntity<>(requestDto, headers);

        // Act
        ResponseEntity<String> response = restTemplate.postForEntity("/api/products", requestEntity, String.class);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void anyAuthenticatedUserShouldBeAbleToViewProducts() {
        // Arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(userToken); // Use the regular user token
        var requestEntity = new HttpEntity<>(headers);

        // Act
        ResponseEntity<ProductResponseDto[]> response = restTemplate.exchange("/api/products", HttpMethod.GET, requestEntity, ProductResponseDto[].class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
