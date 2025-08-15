package dev.bendonaldson.inventorio.service;

import dev.bendonaldson.inventorio.dto.OrderItemRequestDto;
import dev.bendonaldson.inventorio.dto.OrderRequestDto;
import dev.bendonaldson.inventorio.exception.InsufficientStockException;
import dev.bendonaldson.inventorio.exception.ResourceNotFoundException;
import dev.bendonaldson.inventorio.model.Order;
import dev.bendonaldson.inventorio.model.OrderItem;
import dev.bendonaldson.inventorio.model.Product;
import dev.bendonaldson.inventorio.repository.OrderRepository;
import dev.bendonaldson.inventorio.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling business logic related to Orders.
 * This class orchestrates interactions between Order, OrderItem, and Product entities.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order save(OrderRequestDto orderDto) {
        Order order = new Order();

        for (OrderItemRequestDto itemDto : orderDto.items()) {
            Product product = productRepository.findById(itemDto.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + itemDto.productId() + " not found."));

            if (product.getStockQuantity() < itemDto.quantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName() + ". Available: " + product.getStockQuantity());
            }

            product.setStockQuantity(product.getStockQuantity() - itemDto.quantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem(product, itemDto.quantity(), product.getPrice());
            order.addOrderItem(orderItem);
        }

        return orderRepository.save(order);
    }

    public void deleteById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id " + id);
        }

        orderRepository.deleteById(id);
    }
}
