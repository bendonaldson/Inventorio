package dev.bendonaldson.inventorio.service;

import dev.bendonaldson.inventorio.dto.ProductRequestDto;
import dev.bendonaldson.inventorio.exception.ResourceNotFoundException;
import dev.bendonaldson.inventorio.model.Category;
import dev.bendonaldson.inventorio.model.Product;
import dev.bendonaldson.inventorio.repository.CategoryRepository;
import dev.bendonaldson.inventorio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product save(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.categoryId()));
            product.setCategory(category);
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, ProductRequestDto dto) {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));

        productToUpdate.setName(dto.name());
        productToUpdate.setDescription(dto.description());
        productToUpdate.setPrice(dto.price());
        productToUpdate.setStockQuantity(dto.stockQuantity());

        if (dto.categoryId() != null) {
            Category category = categoryRepository.findById(dto.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.categoryId()));
            productToUpdate.setCategory(category);
        } else {
            productToUpdate.setCategory(null);
        }

        return productRepository.save(productToUpdate);
    }

    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id " + id);
        }

        productRepository.deleteById(id);
    }
}