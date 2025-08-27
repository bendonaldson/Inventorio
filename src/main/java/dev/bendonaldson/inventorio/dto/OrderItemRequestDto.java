package dev.bendonaldson.inventorio.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO representing a single item within a new order request.
 *
 * @param productId The ID of the product being ordered. Must not be null.
 * @param quantity  The quantity of the product being ordered. Must be a positive integer.
 */
public record OrderItemRequestDto(
        @NotNull
        Long productId,

        @NotNull
        @Positive(message = "Quantity must be positive")
        int quantity
) {}


