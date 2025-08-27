package dev.bendonaldson.inventorio.repository.specification;

import dev.bendonaldson.inventorio.model.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

/**
 * Provides static methods for creating dynamic {@link Specification} objects for querying {@link Product} entities.
 * These specifications can be combined to build complex, conditional searches.
 */
public class ProductSpecification {

    /**
     * Creates a specification to filter products by name using a case-insensitive "like" search.
     *
     * @param name The product name to search for. If blank, no filter is applied.
     * @return A {@link Specification} for the name filter.
     */
    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isBlank(name)) {
                return criteriaBuilder.conjunction();
            }

            String pattern = "%" + name.toLowerCase() + "%";
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), pattern);
        };
    }

    /**
     * Creates a specification to filter products by their category ID.
     *
     * @param categoryId The ID of the category to filter by. If null, no filter is applied.
     * @return A {@link Specification} for the category filter.
     */
    public static Specification<Product> inCategory(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    /**
     * Creates a specification to filter products within a given price range.
     *
     * @param minPrice The minimum price. If null, no lower bound is set.
     * @param maxPrice The maximum price. If null, no upper bound is set.
     * @return A {@link Specification} for the price range filter.
     */
    public static Specification<Product> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && maxPrice != null) {
                return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            } else if (minPrice != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
            } else if (maxPrice != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
            }
            return criteriaBuilder.conjunction();
        };
    }
}