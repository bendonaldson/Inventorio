package dev.bendonaldson.inventorio.service;

import dev.bendonaldson.inventorio.dto.OrderItemRequestDto;
import dev.bendonaldson.inventorio.dto.OrderRequestDto;
import dev.bendonaldson.inventorio.exception.InsufficientStockException;
import dev.bendonaldson.inventorio.model.Product;
import dev.bendonaldson.inventorio.repository.OrderRepository;
import dev.bendonaldson.inventorio.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the OrderService.
 * Focuses on the business logic of order creation and stock management.
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void save_shouldDeductStockAndCreateOrder_whenStockIsSufficient() {
        // Arrange
        var product = new Product("Laptop", "A good laptop", new BigDecimal("1200.00"), 10, null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        var orderItemDto = new OrderItemRequestDto(1L, 2);
        var orderRequestDto = new OrderRequestDto(List.of(orderItemDto));

        // Act
        orderService.save(orderRequestDto);

        // Assert
        // Check that the stock was correctly deducted
        assertEquals(8, product.getStockQuantity());
        verify(productRepository, times(1)).save(product);
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void save_shouldThrowException_whenStockIsInsufficient() {
        // Arrange
        var product = new Product("Mouse", "A good mouse", new BigDecimal("50.00"), 1, null);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        var orderItemDto = new OrderItemRequestDto(1L, 5); // Trying to order 5, but only 1 is in stock
        var orderRequestDto = new OrderRequestDto(List.of(orderItemDto));

        // Act & Assert
        assertThrows(InsufficientStockException.class, () -> orderService.save(orderRequestDto));
        // Verify that stock quantity remains unchanged and nothing was saved
        assertEquals(1, product.getStockQuantity());
        verify(productRepository, never()).save(any());
        verify(orderRepository, never()).save(any());
    }
}