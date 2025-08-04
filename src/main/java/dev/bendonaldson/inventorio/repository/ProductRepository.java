package dev.bendonaldson.inventorio.repository;

import dev.bendonaldson.inventorio.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for handling Product data access.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
    List<Product> findByCategoryId(Long categoryId);
}