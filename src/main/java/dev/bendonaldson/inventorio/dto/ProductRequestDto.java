package dev.bendonaldson.inventorio.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

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

