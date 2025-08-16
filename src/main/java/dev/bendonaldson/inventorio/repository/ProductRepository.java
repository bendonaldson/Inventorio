package dev.bendonaldson.inventorio.repository;

import dev.bendonaldson.inventorio.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Repository for handling Product data access.
 */
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findByName(String name);
    List<Product> findByCategoryId(Long categoryId);
}