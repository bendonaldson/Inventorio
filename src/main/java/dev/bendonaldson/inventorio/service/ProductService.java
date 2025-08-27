package dev.bendonaldson.inventorio.service;

import dev.bendonaldson.inventorio.dto.ProductRequestDto;
import dev.bendonaldson.inventorio.exception.ResourceNotFoundException;
import dev.bendonaldson.inventorio.model.Category;
import dev.bendonaldson.inventorio.model.Product;
import dev.bendonaldson.inventorio.repository.CategoryRepository;
import dev.bendonaldson.inventorio.repository.ProductRepository;
import dev.bendonaldson.inventorio.repository.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for handling business logic related to Products.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Finds products based on a dynamic set of filter criteria.
     *
     * @param name The name to search for (partial, case-insensitive).
     * @param categoryId The category ID to filter by.
     * @param minPrice The minimum price to filter by.
     * @param maxPrice The maximum price to filter by.
     * @return A list of {@link Product} entities matching the criteria.
     */
    public List<Product> findAll(String name, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Product> spec = ProductSpecification.hasName(name)
                .and(ProductSpecification.inCategory(categoryId))
                .and(ProductSpecification.priceBetween(minPrice, maxPrice));

        return productRepository.findAll(spec);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product save(ProductRequestDto dto) {
        Product product = new Product();
        mapDtoToProduct(product, dto, false);
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, ProductRequestDto dto) {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        mapDtoToProduct(productToUpdate, dto, true);
        return productRepository.save(productToUpdate);
    }

    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }
        productRepository.deleteById(id);
    }

    /**
     * Maps the fields from a ProductRequestDto to a Product entity. This new private
     * method centralizes the mapping logic used by both save() and update().
     *
     * @param product The product entity to update.
     * @param dto The DTO containing the new values.
     * @param allowNullCategory If true, sets the category to null if the dto.categoryId() is null.
     */
    private void mapDtoToProduct(Product product, ProductRequestDto dto, boolean allowNullCategory) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.categoryId()));
            product.setCategory(category);
        } else if (allowNullCategory) {
            product.setCategory(null);
        }
    }
}