package dev.bendonaldson.inventorio.repository;

import dev.bendonaldson.inventorio.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Spring Data JPA repository for {@link Category} entities.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Finds categories by their exact name.
     * @param name The name to search for.
     * @return A list of matching categories.
     */
    List<Category> findByName(String name);

    /**
     * Finds categories by name, ignoring case and matching partial strings.
     * @param name The partial name to search for.
     * @return A list of matching categories.
     */
    List<Category> findByNameContainingIgnoreCase(String name);
}