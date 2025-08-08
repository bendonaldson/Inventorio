package dev.bendonaldson.inventorio.repository;

import dev.bendonaldson.inventorio.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
