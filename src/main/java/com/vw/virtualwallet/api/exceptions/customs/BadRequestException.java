package com.vw.virtualwallet.api.exceptions.customs;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Custom exception class to handle bad request scenarios.
 * This exception is thrown when the client sends a request that cannot be processed due to client error (e.g., malformed request syntax).
 *
 * @author caito
 *
 */
@Getter
public class BadRequestException extends RuntimeException{
    List<String> errors = new ArrayList<>();
    public BadRequestException(List<String> errors) {
        this.errors = errors;
    }
}
