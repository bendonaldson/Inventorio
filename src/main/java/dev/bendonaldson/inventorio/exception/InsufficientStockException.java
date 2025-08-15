package dev.bendonaldson.inventorio.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String message){
        super(message);
    }
}
