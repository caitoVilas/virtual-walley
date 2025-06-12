package com.vw.virtualwallet.configs.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vw.virtualwallet.api.models.responses.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * EntryPoint class that implements AuthenticationEntryPoint to handle unauthorized access attempts.
 * It returns a JSON response with an error message and status code when authentication fails.
 *
 * @author caito
 *
 */
@Component
public class EntryPoint implements AuthenticationEntryPoint {

    /**
     * Handles unauthorized access attempts by returning a JSON response with an error message and status code.
     *
     * @param request the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param authException the AuthenticationException that occurred
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        final String MSG = "Unauthorized";
        var res = ErrorResponse.builder()
                .code(HttpStatus.UNAUTHORIZED.value())
                .status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .message(MSG)
                .method(request.getMethod())
                .path(request.getRequestURL().toString())
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String apiError = mapper.writeValueAsString(res);
        response.getWriter().write(apiError);
    }
}
