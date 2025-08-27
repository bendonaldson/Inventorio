package dev.bendonaldson.inventorio.exception;

/**
 * Exception thrown during user registration when the requested username
 * is already taken.
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}