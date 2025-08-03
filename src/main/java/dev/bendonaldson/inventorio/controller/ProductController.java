package dev.bendonaldson.inventorio.controller;

import dev.bendonaldson.inventorio.model.Product;
import dev.bendonaldson.inventorio.service.ProductService;
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
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    // Create a Product
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }

    // Update a Product
    @PutMapping
    public Product update(@RequestBody Product product) {
        return productService.save(product);
    }

    // Delete a Product
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }

    // Find Product by Name
    @GetMapping("/find/name/{name}")
    public List<Product> findByName(@PathVariable String name) {
        return productService.findByName(name);
    }
}
