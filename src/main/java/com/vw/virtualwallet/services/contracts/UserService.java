package com.vw.virtualwallet.services.contracts;

import com.vw.virtualwallet.api.models.requests.UserRequest;
import com.vw.virtualwallet.api.models.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

/**
 * UserService interface for managing user-related operations.
 * This service provides methods to create and manage users in the virtual wallet system.
 *
 * @author caito
 *
 */
public interface UserService {
    void createUser(UserRequest request);
    UserResponse getByEmail(String email);
    UserResponse getUser(Authentication authentication);
    Page<UserResponse> getUsers(int page, int size);
    UserResponse updateUser(String email, UserRequest request, Authentication authentication);
    void deleteUser(String email);
}
