package com.vw.virtualwallet.api.controllers.contracts;

import com.vw.virtualwallet.api.models.requests.UserRequest;
import com.vw.virtualwallet.api.models.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * UserController interface defines the contract for user-related operations.
 * It provides an endpoint to create a new user.
 *
 * @author caito
 *
 */
public interface UserController {

    @PostMapping("/create")
    @Operation(summary = "Create a new user")
    @Parameter(name = "request", description = "User data for creation")
    @ApiResponses({
            @ApiResponse(responseCode = "202", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, validation failed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createUser(@RequestBody UserRequest request);

    @GetMapping("/email")
    @Operation(summary = "Get user by email")
    @Parameter(name = "email", description = "Email of the user to retrieve")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponse> getUserByEmail(@Parameter() String email);
}
