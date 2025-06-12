package com.vw.virtualwallet.api.controllers.impl;

import com.vw.virtualwallet.api.controllers.contracts.AuthenticationController;
import com.vw.virtualwallet.api.models.requests.AuthenticationRequest;
import com.vw.virtualwallet.api.models.responses.AuthenticationResponse;
import com.vw.virtualwallet.services.contracts.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API", description = "Operations related to user authentication")
public class AuthenticationControllerImpl implements AuthenticationController {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
