package dev.bendonaldson.inventorio;

import dev.bendonaldson.inventorio.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main entry point for the Inventorio Spring Boot application.
 */
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class InventorioApplication {

    /**
     * Main method which serves as the entry point for the Spring Boot application.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(InventorioApplication.class, args);
    }
}
