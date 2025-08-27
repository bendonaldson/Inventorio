package dev.bendonaldson.inventorio.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * DTO for creating a new customer order.
 *
 * @param items A list of {@link OrderItemRequestDto} representing the products to be ordered.
 */
public record OrderRequestDto(
        @NotNull
        List<OrderItemRequestDto> items
) {}
