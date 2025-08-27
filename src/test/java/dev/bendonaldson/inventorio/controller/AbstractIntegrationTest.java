package dev.bendonaldson.inventorio.controller;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Abstract base class for all integration tests.
 * Manages the lifecycle of a PostgreSQL Testcontainer, ensuring a clean database
 * is available for the test suite.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    // The static keyword ensures the container is started only once for all tests in this class hierarchy
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    /**
     * Dynamically configures the datasource properties to point to the running Testcontainer.
     * This method overrides the properties in application.properties for the test context.
     */
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        // Use 'create-drop' for tests to ensure a clean slate for each test run
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }
}