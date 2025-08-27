package dev.bendonaldson.inventorio.dto;

import java.math.BigDecimal;

/**
 * DTO for sending detailed information about a single item within an order.
 *
 * @param id                    The unique identifier of the order item.
 * @param quantity              The quantity of the product ordered.
 * @param priceAtTimeOfPurchase The price of the product at the time the order was placed.
 * @param product               A {@link ProductResponseDto} with details of the ordered product.
 */
public record OrderItemResponseDto(
        Long id,
        int quantity,
        BigDecimal priceAtTimeOfPurchase,
        ProductResponseDto product
) {}
