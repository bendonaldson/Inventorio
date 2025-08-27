package dev.bendonaldson.inventorio.exception;

/**
 * Exception thrown when a requested resource (e.g., a Product, Category, or Order)
 * cannot be found in the database.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
