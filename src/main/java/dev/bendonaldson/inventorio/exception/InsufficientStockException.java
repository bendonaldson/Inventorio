package dev.bendonaldson.inventorio.exception;

/**
 * Exception thrown when an attempt is made to order a product
 * with a quantity greater than the available stock.
 */
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }
}
