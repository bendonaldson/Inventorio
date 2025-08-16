package dev.bendonaldson.inventorio;

import dev.bendonaldson.inventorio.config.JwtProperties;
import dev.bendonaldson.inventorio.model.Role;
import dev.bendonaldson.inventorio.model.User;
import dev.bendonaldson.inventorio.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class InventorioApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventorioApplication.class, args);
    }

    /**
     * This bean runs on application startup and creates an initial admin user
     * if one does not already exist in the database.
     */
    @Bean
    CommandLineRunner adminUserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        Role.ADMIN
                );
                userRepository.save(admin);
                System.out.println("✅ Admin user created successfully!");
            }
        };
    }
}
