package dev.bendonaldson.inventorio.repository;

import dev.bendonaldson.inventorio.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository for handling Category data access.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);
    List<Category> findByNameContainingIgnoreCase(String name);
}