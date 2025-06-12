package com.vw.virtualwallet.configs.security;

import com.vw.virtualwallet.api.exceptions.customs.NotFoundException;
import com.vw.virtualwallet.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

/**
 * Configuration class for security beans.
 * It defines the authentication provider, password encoder, and user details service.
 *
 * @author caito
 *
 */
@Configuration
@RequiredArgsConstructor
public class BeansConfig {
    private final UserRepository userRepository;

    /**
     * Bean for the password encoder.
     * It uses BCryptPasswordEncoder for encoding passwords.
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for the authentication manager.
     * It retrieves the authentication manager from the provided configuration.
     *
     * @param configuration AuthenticationConfiguration object
     * @return AuthenticationManager
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Bean for the user details service.
     * It retrieves user details by email from the user repository.
     *
     * @return UserDetailsService
     */
    @Bean
    @Transactional(readOnly = true)
    public UserDetailsService userDetailsService(){
        return (user) -> userRepository.findByEmail(user).orElseThrow(()->
                new NotFoundException("User not found with email: " + user));
    }

    /**
     * Bean for the authentication provider.
     * It uses a DaoAuthenticationProvider with a UserDetailsService and a password encoder.
     *
     * @param userDetailsService UserDetailsService for loading user details
     * @return AuthenticationProvider
     */
    @Bean
    AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
