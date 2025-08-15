package dev.bendonaldson.inventorio.dto;

import dev.bendonaldson.inventorio.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        LocalDateTime orderDate,
        OrderStatus status,
        List<OrderItemResponseDto> orderItems
) {}
