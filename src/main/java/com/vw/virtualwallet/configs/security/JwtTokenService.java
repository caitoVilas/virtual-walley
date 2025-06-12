package com.vw.virtualwallet.configs.security;

import com.vw.virtualwallet.persistence.entities.UserApp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Service class for handling JWT token operations such as generation, validation, and extraction of claims.
 * It uses a secret key to sign the tokens and provides methods to generate and validate JWTs.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenService {

    /**
     * Generates a JWT token for the given user details.
     *
     * @param userDetails User details for which the token is generated
     * @return            Generated JWT token as a String
     */
    public String generateToken(UserDetails userDetails) {
        var user = (UserApp) userDetails;
        // 24 hours in milliseconds
        long JWT_EXPIRATION = 1000 * 60 * 24;
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("owner", user.getFullName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .claim("roles", this.getRoles(userDetails))
                .signWith(this.getSigningKey())
                .compact();
    }

    /**
     * Validates the JWT token against the provided user details.
     *
     * @param token        JWT token to be validated
     * @param userDetails  User details for which the token is validated
     * @return             True if the token is valid, false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    /**
     * Extracts the username from the JWT token.
     *
     * @param token JWT token from which the username is extracted
     * @return      Username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extracts claims from the JWT token using a provided function.
     *
     * @param token           JWT token from which claims are extracted
     * @param claimsResolver  Function to resolve claims from the token
     * @param <T>             Type of the claims to be resolved
     * @return                Resolved claims of type T
     */
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Retrieves the roles from the user details.
     *
     * @param userDetails User details from which roles are extracted
     * @return            Set of roles associated with the user
     */
    private Set<String> getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token JWT token from which all claims are extracted
     * @return      Claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the JWT token is expired.
     *
     * @param token JWT token to be checked for expiration
     * @return      True if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token JWT token from which the expiration date is extracted
     * @return      Expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    /**
     * Retrieves the signing key used for signing the JWT tokens.
     *
     * @return Key used for signing the JWT tokens
     */
    private Key getSigningKey() {
        String SECRET_KEY = "Hw9z1Yk8Nmq1IzlwcCg8j6yHzw6RKjzZUi9r7Ww555o0PP";
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
