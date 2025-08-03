package dev.bendonaldson.inventorio;

import dev.bendonaldson.inventorio.model.Product;
import dev.bendonaldson.inventorio.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorioApplication.class, args);
    }

    @Autowired
    ProductRepository productRepository;

    @Bean
    @ConditionalOnProperty(prefix = "app", name = "db.init.enabled", havingValue = "true")
    CommandLineRunner runner(ProductRepository productRepository) {
        return args -> {
            productRepository.save(new Product("Laptop", "Powerful laptop for developers", 1500.00, 10));
            productRepository.save(new Product("Keyboard", "Mechanical keyboard with RGB lighting", 120.00, 50));
            productRepository.save(new Product("Mouse", "Ergonomic wireless mouse", 75.50, 100));
        };
    }
}
