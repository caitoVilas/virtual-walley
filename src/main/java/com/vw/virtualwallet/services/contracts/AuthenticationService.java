package com.vw.virtualwallet.services.contracts;

import com.vw.virtualwallet.api.models.requests.AuthenticationRequest;
import com.vw.virtualwallet.api.models.responses.AuthenticationResponse;

/**
 * AuthenticationService interface defines the contract for user authentication operations.
 * It provides a method to authenticate a user based on their credentials.
 *
 * @author caito
 *
 */
public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
