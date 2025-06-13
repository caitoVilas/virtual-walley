package com.vw.virtualwallet.api.controllers.impl;

import com.vw.virtualwallet.api.controllers.contracts.UserController;
import com.vw.virtualwallet.api.models.requests.UserRequest;
import com.vw.virtualwallet.api.models.responses.UserResponse;
import com.vw.virtualwallet.services.contracts.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "User management operations")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<?> createUser(UserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<UserResponse> getUserByEmail(String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @Override
    public ResponseEntity<UserResponse> getAuthenticatedUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser(authentication));
    }

    @Override
    public ResponseEntity<?> getAllUsers(int page, int size) {
        var users = userService.getUsers(page, size);
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(String email, UserRequest request, Authentication authentication) {
        return ResponseEntity.ok(userService.updateUser(email, request, authentication));
    }

    @Override
    public ResponseEntity<?> deleteUser(String email, Authentication authentication) {
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }
}
