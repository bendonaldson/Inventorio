package dev.bendonaldson.inventorio.controller;

import dev.bendonaldson.inventorio.model.Category;
import dev.bendonaldson.inventorio.service.CategoryService;
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
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * REST controller for managing product categories.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id) {
        return categoryService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id " + id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Category create(@Valid @RequestBody Category category) {
        return categoryService.save(category);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @Valid @RequestBody Category category) {
        if (categoryService.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id " + id);
        }
        category.setId(id);
        return categoryService.save(category);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}