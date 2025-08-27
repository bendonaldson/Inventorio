package dev.bendonaldson.inventorio.mapper;

import dev.bendonaldson.inventorio.dto.CategoryDto;
import dev.bendonaldson.inventorio.model.Category;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link Category} entities and {@link CategoryDto} DTOs.
 */
@Component
public class CategoryMapper {

    /**
     * Converts a Category entity to a CategoryDto.
     * @param category The Category entity.
     * @return The corresponding CategoryDto.
     */
    public CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryDto(category.getId(), category.getName());
    }

    /**
     * Converts a CategoryDto to a Category entity.
     * @param dto The CategoryDto.
     * @return The corresponding Category entity.
     */
    public Category toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setId(dto.id());
        category.setName(dto.name());
        return category;
    }
}