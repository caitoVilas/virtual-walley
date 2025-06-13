package com.vw.virtualwallet.api.controllers.contracts;

import com.vw.virtualwallet.api.models.requests.UserRequest;
import com.vw.virtualwallet.api.models.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createUser(@RequestBody UserRequest request) throws MessagingException;

    @GetMapping("/email")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get user by email only for ADMIN")
    @Parameter(name = "email", description = "Email of the user to retrieve")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponse> getUserByEmail(@Parameter() String email);

    @GetMapping
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authenticated user retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponse> getAuthenticatedUser(Authentication authentication);

    @GetMapping("/all")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get all users only for ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No users found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAllUsers(@Parameter(description = "Page number", example = "0") int page,
                                         @Parameter(description = "Page size", example = "10") int size);

    @PutMapping("/update")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Update user by email")
    @Parameters({
            @Parameter(name = "email", description = "Email of the user to update"),
            @Parameter(name = "request", description = "User data for update")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, validation failed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponse> updateUser(@Parameter() String email, @RequestBody UserRequest request,
                                                   Authentication authentication);

    @DeleteMapping("/delete")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Delete user by email only for ADMIN")
    @Parameter(name = "email", description = "Email of the user to delete")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> deleteUser(@Parameter() String email, Authentication authentication);

    @GetMapping("/activate-account")
    @Operation(summary = "Activate user account")
    @Parameter(name = "token", description = "Activation code for the user account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User account activated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, activation code invalid or expired"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> activateUserAccount(@Parameter() String token) throws MessagingException;
}
