package com.vw.virtualwallet.services.contracts;

import com.vw.virtualwallet.api.models.requests.UserRequest;
import com.vw.virtualwallet.api.models.responses.UserResponse;

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
}
