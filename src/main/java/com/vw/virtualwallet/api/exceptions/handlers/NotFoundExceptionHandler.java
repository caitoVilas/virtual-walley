package com.vw.virtualwallet.api.exceptions.handlers;

import com.vw.virtualwallet.api.exceptions.customs.NotFoundException;
import com.vw.virtualwallet.api.models.responses.ErrorResponse;
import com.vw.virtualwallet.utils.logs.WriteLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Exception handler for NotFoundException.
 * This class handles exceptions of type NotFoundException and returns a structured error response.
 * It uses @RestControllerAdvice to apply globally to all controllers.
 *
 * @author Caito
 */
@RestControllerAdvice
@Slf4j
public class NotFoundExceptionHandler {
    /**
     * Handles NotFoundException and returns a ResponseEntity with an ErrorResponse.
     *
     * @param ex      the NotFoundException that was thrown
     * @param request the HttpServletRequest that triggered the exception
     * @return a ResponseEntity containing the error response
     */
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, HttpServletRequest request){
        log.error(WriteLog.logError(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .code(HttpStatus.NOT_FOUND.value())
                        .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .timestamp(LocalDateTime.now())
                        .message(ex.getMessage())
                        .method(request.getMethod())
                        .path(request.getRequestURL().toString())
                        .build()
        );
    }
}
