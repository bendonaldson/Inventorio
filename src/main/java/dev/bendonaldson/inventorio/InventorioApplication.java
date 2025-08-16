package dev.bendonaldson.inventorio;

import dev.bendonaldson.inventorio.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class InventorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorioApplication.class, args);
    }
}
