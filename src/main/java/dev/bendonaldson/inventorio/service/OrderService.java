package dev.bendonaldson.inventorio.service;

import dev.bendonaldson.inventorio.model.Order;
import dev.bendonaldson.inventorio.model.OrderItem;
import dev.bendonaldson.inventorio.model.Product;
import dev.bendonaldson.inventorio.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling business logic related to Orders.
 * This class orchestrates interactions between Order, OrderItem, and Product entities.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Creates a new order and processes its items.
     * This method is transactional to ensure data consistency.
     *
     * @param order The order object to save, containing its order items.
     * @return The saved order.
     * @throws ResponseStatusException if product stock is insufficient.
     */
    @Transactional
    public Order save(Order order) {
        // Ensure the bidirectional relationship is set before saving
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                item.setOrder(order);
                Product product = productService.findById(item.getProduct().getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Product with ID " + item.getProduct().getId() + " not found."));

                if (product.getStockQuantity() < item.getQuantity()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Insufficient stock for product: " + product.getName() + ". Available: " + product.getStockQuantity());
                }

                // Deduct stock and set current price
                product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
                item.setPriceAtTimeOfPurchase(product.getPrice());
                item.setProduct(product);
                productService.save(product, product.getCategory() != null ? product.getCategory().getId() : null);
            }
        }

        return orderRepository.save(order);
    }

    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

}
