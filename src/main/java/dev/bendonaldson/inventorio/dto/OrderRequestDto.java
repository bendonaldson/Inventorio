package dev.bendonaldson.inventorio.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDto(
        @NotNull
        List<OrderItemRequestDto> items
) {}
