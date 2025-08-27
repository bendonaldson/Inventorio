package dev.bendonaldson.inventorio.controller;

import dev.bendonaldson.inventorio.dto.ProductRequestDto;
import dev.bendonaldson.inventorio.dto.ProductResponseDto;
import dev.bendonaldson.inventorio.exception.ResourceNotFoundException;
import dev.bendonaldson.inventorio.mapper.ProductMapper;
import dev.bendonaldson.inventorio.model.Product;
import dev.bendonaldson.inventorio.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing product-related API endpoints.
 * Provides full CRUD functionality as well as search and filtering capabilities.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    /**
     * Retrieves a list of all products, with optional filtering.
     *
     * @param name Optional. A string to search for in product names (case-insensitive).
     * @param categoryId Optional. The ID of a category to filter by.
     * @param minPrice Optional. The minimum price for a price range filter.
     * @param maxPrice Optional. The maximum price for a price range filter.
     * @return A list of {@link ProductResponseDto} objects matching the criteria.
     */
    @GetMapping
    public List<ProductResponseDto> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return productService.findAll(name, categoryId, minPrice, maxPrice).stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The {@link ProductResponseDto} object.
     * @throws ResourceNotFoundException if the product with the given ID is not found.
     */
    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productService.findById(id)
                .map(productMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    /**
     * Creates a new product.
     *
     * @param productDto The DTO containing the details of the product to create.
     * @return The created {@link ProductResponseDto}, including its new ID.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto productDto) {
        Product savedProduct = productService.save(productDto);
        return productMapper.toResponseDto(savedProduct);
    }

    /**
     * Updates an existing product.
     *
     * @param id The ID of the product to update.
     * @param productDto The DTO containing the updated details.
     * @return The updated {@link ProductResponseDto}.
     */
    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @Valid @RequestBody ProductRequestDto productDto) {
        Product updatedProduct = productService.update(id, productDto);
        return productMapper.toResponseDto(updatedProduct);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
