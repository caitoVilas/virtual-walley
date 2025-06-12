package com.vw.virtualwallet.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Caito
 * ErrorResponse class represents the structure of an error response in the API.
 * It includes fields for error code, status, timestamp, message, method, and path.
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class ErrorResponse implements Serializable {
    private int code;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String method;
    private String path;
}
