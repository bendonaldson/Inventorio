package dev.bendonaldson.inventorio.repository;

import dev.bendonaldson.inventorio.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link OrderItem} entities.
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
