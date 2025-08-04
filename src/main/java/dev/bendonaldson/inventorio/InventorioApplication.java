package dev.bendonaldson.inventorio;

import dev.bendonaldson.inventorio.model.Category;
import dev.bendonaldson.inventorio.model.Product;
import dev.bendonaldson.inventorio.repository.CategoryRepository;
import dev.bendonaldson.inventorio.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorioApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ProductRepository productRepository, CategoryRepository categoryRepository) {
        return args -> {
            Category electronics = categoryRepository.save(new Category("Electronics"));

            productRepository.save(new Product("Laptop", "Powerful laptop for developers", 1500.00, 10, electronics));
            productRepository.save(new Product("Keyboard", "Mechanical keyboard with RGB lighting", 120.00, 50, electronics));
            productRepository.save(new Product("Mouse", "Ergonomic wireless mouse", 75.50, 100, electronics));
        };
    }
}
