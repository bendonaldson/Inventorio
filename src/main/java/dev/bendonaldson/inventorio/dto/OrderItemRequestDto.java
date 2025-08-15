package dev.bendonaldson.inventorio.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequestDto(
        @NotNull
        Long productId,

        @NotNull
        @Positive(message = "Quantity must be positive")
        int quantity
) {}


