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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing product-related API endpoints.
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

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return productService.findAll().stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        return productService.findById(id)
                .map(productMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponseDto create(@Valid @RequestBody ProductRequestDto productDto) {
        Product savedProduct = productService.save(productDto);
        return productMapper.toResponseDto(savedProduct);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @Valid @RequestBody ProductRequestDto productDto) {
        Product updatedProduct = productService.update(id, productDto);
        return productMapper.toResponseDto(updatedProduct);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
