package dev.bendonaldson.inventorio.repository;

import dev.bendonaldson.inventorio.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link Order} entities.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
