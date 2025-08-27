package dev.bendonaldson.inventorio.dto;

import java.math.BigDecimal;

/**
 * DTO for sending detailed product information to the client.
 *
 * @param id            The unique identifier of the product.
 * @param name          The name of the product.
 * @param description   The product's description.
 * @param price         The current price of the product.
 * @param stockQuantity The available quantity in stock.
 * @param category      A {@link CategoryDto} representing the product's category.
 */
public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stockQuantity,
        CategoryDto category
) {}
