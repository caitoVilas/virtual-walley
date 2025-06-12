package com.vw.virtualwallet.services.impl;

import com.vw.virtualwallet.api.models.requests.AuthenticationRequest;
import com.vw.virtualwallet.api.models.responses.AuthenticationResponse;
import com.vw.virtualwallet.configs.security.JwtTokenService;
import com.vw.virtualwallet.services.contracts.AuthenticationService;
import com.vw.virtualwallet.utils.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

/**
 * Implementation of the AuthenticationService interface.
 * Handles user authentication by validating credentials and generating JWT tokens.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    /**
     * Authenticates a user by validating their credentials and generating a JWT token.
     *
     * @param request the authentication request containing user credentials
     * @return an AuthenticationResponse containing the generated JWT token
     */
    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        log.info(WriteLog.logInfo("Authenticating user service"));
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = auth.getPrincipal();
        var jwt = jwtTokenService.generateToken((UserDetails) user);
        return AuthenticationResponse.builder()
                .access_token(jwt)
                .build();
    }
}
