package dev.bendonaldson.inventorio.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * DTO for creating or updating a product.
 *
 * @param name          The name of the product. Must not be blank.
 * @param description   The product's description. Must not be blank.
 * @param price         The price of the product. Must be a positive value greater than 0.01.
 * @param stockQuantity The available quantity in stock. Must be a positive integer.
 * @param categoryId    The ID of the category this product belongs to.
 */
public record ProductRequestDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Description is required")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be a positive value")
        BigDecimal price,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Stock quantity must be a positive value")
        int stockQuantity,

        Long categoryId
) {}

