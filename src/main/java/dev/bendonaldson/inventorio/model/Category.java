package dev.bendonaldson.inventorio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Represents a product category in the database.
 * A category can be associated with multiple products.
 */
@Getter
@Setter
@ToString(exclude = "products")
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the category (e.g., "Electronics", "Books").
     */
    @NotBlank(message = "Name is required")
    private String name;

    /**
     * The list of products belonging to this category.
     * This is the inverse side of the one-to-many relationship.
     */
    @OneToMany(mappedBy = "category")
    @JsonIgnore // Prevents infinite recursion during JSON serialization.
    private List<Product> products;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }
}
