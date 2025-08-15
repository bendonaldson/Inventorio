package dev.bendonaldson.inventorio.mapper;

import dev.bendonaldson.inventorio.dto.CategoryDto;
import dev.bendonaldson.inventorio.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryDto(category.getId(), category.getName());
    }

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