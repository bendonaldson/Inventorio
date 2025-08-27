package dev.bendonaldson.inventorio.repository;

import dev.bendonaldson.inventorio.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring Data JPA repository for {@link Product} entities.
 * Extends JpaSpecificationExecutor to support dynamic, criteria-based queries.
 */
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}