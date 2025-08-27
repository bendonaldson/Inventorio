package dev.bendonaldson.inventorio.mapper;

import dev.bendonaldson.inventorio.dto.OrderItemResponseDto;
import dev.bendonaldson.inventorio.dto.OrderResponseDto;
import dev.bendonaldson.inventorio.model.Order;
import dev.bendonaldson.inventorio.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

/**
 * Mapper for converting {@link Order} entities to {@link OrderResponseDto} DTOs.
 * This mapper handles nested mapping of order items.
 */
@Component
public class OrderMapper {

    private final ProductMapper productMapper;

    @Autowired
    public OrderMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     * Converts an Order entity to a fully detailed OrderResponseDto.
     * @param order The Order entity.
     * @return The corresponding OrderResponseDto, including all its items.
     */
    public OrderResponseDto toResponseDto(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderResponseDto(
                order.getId(),
                order.getOrderDate(),
                order.getStatus(),
                order.getOrderItems().stream()
                        .map(this::toOrderItemResponseDto)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Private helper method to convert an OrderItem entity to an OrderItemResponseDto.
     * @param item The OrderItem entity.
     * @return The corresponding OrderItemResponseDto.
     */
    private OrderItemResponseDto toOrderItemResponseDto(OrderItem item) {
        if (item == null) {
            return null;
        }

        return new OrderItemResponseDto(
                item.getId(),
                item.getQuantity(),
                item.getPriceAtTimeOfPurchase(),
                productMapper.toResponseDto(item.getProduct())
        );
    }
}