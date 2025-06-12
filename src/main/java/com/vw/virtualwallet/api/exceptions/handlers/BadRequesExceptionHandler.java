package com.vw.virtualwallet.api.exceptions.handlers;

import com.vw.virtualwallet.api.exceptions.customs.BadRequestException;
import com.vw.virtualwallet.api.models.responses.ErrorsResponse;
import com.vw.virtualwallet.utils.logs.WriteLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Exception handler for BadRequestException.
 * This class handles exceptions of type BadRequestException and returns a structured error response.
 * It uses @RestControllerAdvice to apply globally to all controllers.
 *
 * @author Caito
 */
@RestControllerAdvice
@Slf4j
public class BadRequesExceptionHandler {
    /**
     * Handles BadRequestException and returns a ResponseEntity with error details.
     *
     * @param ex      the BadRequestException that was thrown
     * @param request the HttpServletRequest that caused the exception
     * @return a ResponseEntity containing the error details
     */
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorsResponse> handleBadRequestException(BadRequestException ex,
                                                                       HttpServletRequest request) {
        log.error(WriteLog.logError("Validation errors: " + String.join(", ", ex.getErrors())));
        return ResponseEntity.badRequest().body(
                ErrorsResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .timestamp(LocalDateTime.now())
                        .messages(ex.getErrors())
                        .method(request.getMethod())
                        .path(request.getRequestURL().toString())
                        .build()
        );
    }
}
