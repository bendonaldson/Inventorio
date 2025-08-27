package dev.bendonaldson.inventorio.dto;

import dev.bendonaldson.inventorio.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for sending detailed order information to the client.
 *
 * @param id          The unique identifier of the order.
 * @param orderDate   The timestamp when the order was placed.
 * @param status      The current status of the order (e.g., PENDING, SHIPPED).
 * @param orderItems  A list of {@link OrderItemResponseDto} detailing the items in the order.
 */
public record OrderResponseDto(
        Long id,
        LocalDateTime orderDate,
        OrderStatus status,
        List<OrderItemResponseDto> orderItems
) {}
