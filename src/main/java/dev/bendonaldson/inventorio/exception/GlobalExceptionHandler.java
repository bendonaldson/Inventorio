package dev.bendonaldson.inventorio.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the Inventorio application.
 * This class intercepts exceptions thrown by controllers and services
 * and formats them into consistent JSON error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions for request bodies annotated with @Valid.
     * @param ex The exception thrown when request body validation fails.
     * @return A map of field names to their corresponding validation error messages.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }

    /**
     * Handles validation exceptions for general constraints (e.g., on path variables).
     * @param ex The exception thrown when a constraint violation occurs.
     * @return A map of field names to their corresponding validation error messages.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            errors.put(fieldName.substring(fieldName.lastIndexOf('.') + 1), violation.getMessage());
        });
        return errors;
    }

    /**
     * Handles the generic ResponseStatusException for ad-hoc HTTP status responses.
     * @param ex The exception containing the HTTP status and reason.
     * @return A map containing the error reason.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public Map<String, String> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getReason());
        return error;
    }

    /**
     * Handles the custom ResourceNotFoundException.
     * @param ex The exception thrown when a resource is not found.
     * @return A map containing the error message.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

    /**
     * Handles the custom InsufficientStockException.
     * @param ex The exception thrown when product stock is insufficient for an order.
     * @return A map containing the error message.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InsufficientStockException.class)
    public Map<String, String> handleInsufficientStockException(InsufficientStockException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

    /**
     * Handles the custom UserAlreadyExistsException.
     * @param ex The exception thrown when a user tries to register with an existing username.
     * @return A map containing the error message.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public Map<String, String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }

    /**
     * Handles the UsernameNotFoundException from Spring Security.
     * @param ex The exception thrown when a user is not found during authentication.
     * @return A map containing the error message.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String, String> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }
}