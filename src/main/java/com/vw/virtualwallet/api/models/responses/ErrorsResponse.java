package com.vw.virtualwallet.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Caito
 * Response model for error messages in the medical clinic API.
 * This class is used to standardize the error responses sent to clients.
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class ErrorsResponse implements Serializable {
    private int code;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private List<String> messages;
    private String method;
    private String path;
}
