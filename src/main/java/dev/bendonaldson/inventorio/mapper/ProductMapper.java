package dev.bendonaldson.inventorio.mapper;

import dev.bendonaldson.inventorio.dto.ProductResponseDto;
import dev.bendonaldson.inventorio.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting {@link Product} entities to {@link ProductResponseDto} DTOs.
 */
@Component
public class ProductMapper {

    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    /**
     * Converts a Product entity to a ProductResponseDto.
     * @param product The Product entity.
     * @return The corresponding ProductResponseDto, including its nested category DTO.
     */
    public ProductResponseDto toResponseDto(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                categoryMapper.toDto(product.getCategory())
        );
    }
}