package dev.bendonaldson.inventorio.service;

import dev.bendonaldson.inventorio.dto.RegisterRequestDto;
import dev.bendonaldson.inventorio.exception.UserAlreadyExistsException;
import dev.bendonaldson.inventorio.model.Role;
import dev.bendonaldson.inventorio.model.User;
import dev.bendonaldson.inventorio.repository.UserRepository;
import dev.bendonaldson.inventorio.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the AuthService.
 * These tests use Mockito to isolate the service logic from its dependencies.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldCreateUser_whenUsernameIsAvailable() {
        // Arrange
        var request = new RegisterRequestDto("newUser", "password");
        when(userRepository.findByUsername("newUser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(jwtService.generateToken(any(User.class))).thenReturn("mockJwtToken");

        // Act
        var response = authService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals("mockJwtToken", response.accessToken());
        // Verify that the save method was called exactly once on the repository
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_shouldThrowException_whenUsernameIsTaken() {
        // Arrange
        var request = new RegisterRequestDto("existingUser", "password");
        var existingUser = new User("existingUser", "pass", Role.USER);
        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> authService.register(request));
        // Verify that the save method was never called
        verify(userRepository, never()).save(any(User.class));
    }
}