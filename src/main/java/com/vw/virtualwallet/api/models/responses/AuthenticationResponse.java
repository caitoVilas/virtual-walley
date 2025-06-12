package com.vw.virtualwallet.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * AuthenticationResponse represents the response for user authentication.
 * It contains the access token generated after successful authentication.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class AuthenticationResponse implements Serializable {
    private String access_token;
}
