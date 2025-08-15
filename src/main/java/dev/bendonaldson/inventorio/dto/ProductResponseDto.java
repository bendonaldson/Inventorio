package dev.bendonaldson.inventorio.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stockQuantity,
        CategoryDto category
) {}
