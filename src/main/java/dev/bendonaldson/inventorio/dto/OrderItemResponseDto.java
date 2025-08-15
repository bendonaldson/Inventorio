package dev.bendonaldson.inventorio.dto;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long id,
        int quantity,
        BigDecimal priceAtTimeOfPurchase,
        ProductResponseDto product
) {}
