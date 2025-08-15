package dev.bendonaldson.inventorio.controller;

import dev.bendonaldson.inventorio.dto.CategoryDto;
import dev.bendonaldson.inventorio.exception.ResourceNotFoundException;
import dev.bendonaldson.inventorio.mapper.CategoryMapper;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing product categories.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryDto findById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryService.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        category.setId(id);
        Category updatedCategory = categoryService.save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteById(id);
    }
}