package dev.bendonaldson.inventorio.dto;

/**
 * DTO for sending a JWT access token back to the client upon successful authentication.
 *
 * @param accessToken The JSON Web Token.
 */
public record AuthResponseDto(String accessToken) {}
