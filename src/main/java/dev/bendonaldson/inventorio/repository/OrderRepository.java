package dev.bendonaldson.inventorio.repository;

import dev.bendonaldson.inventorio.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
