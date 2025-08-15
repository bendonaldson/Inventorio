package dev.bendonaldson.inventorio.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryDto(
    Long id,

    @NotBlank(message = "Category name is required")
    String name
) {}
