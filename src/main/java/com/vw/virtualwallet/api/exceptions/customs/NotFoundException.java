package com.vw.virtualwallet.api.exceptions.customs;

/**
 * Custom exception class to handle bad request scenarios.
 * This exception is thrown when the client sends a request that cannot be processed due to client error (e.g., malformed request syntax).
 *
 * @author caito
 *
 */
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
