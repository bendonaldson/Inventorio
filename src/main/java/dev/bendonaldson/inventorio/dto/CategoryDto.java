package dev.bendonaldson.inventorio.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO representing a product category.
 *
 * @param id   The unique identifier of the category.
 * @param name The name of the category. Must not be blank.
 */
public record CategoryDto(
    Long id,

    @NotBlank(message = "Category name is required")
    String name
) {}
