package dev.bendonaldson.inventorio.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for capturing new user registration details.
 *
 * @param username The desired username. Must not be blank.
 * @param password The desired password. Must not be blank.
 */
public record RegisterRequestDto(
        @NotBlank String username,
        @NotBlank String password
) {}
