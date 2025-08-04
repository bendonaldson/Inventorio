package dev.bendonaldson.inventorio.service;

import dev.bendonaldson.inventorio.model.Category;
import dev.bendonaldson.inventorio.model.Product;
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
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product save(Product product, Long categoryId) {
        if (categoryId != null) {
            Optional<Category> category = categoryService.findById(categoryId);
            category.ifPresent(product::setCategory);
        }
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}