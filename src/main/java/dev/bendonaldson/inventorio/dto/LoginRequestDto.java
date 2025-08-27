package dev.bendonaldson.inventorio.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for capturing user login credentials.
 *
 * @param username The user's username. Must not be blank.
 * @param password The user's password. Must not be blank.
 */
public record LoginRequestDto(
        @NotBlank String username,
        @NotBlank String password
) {}
