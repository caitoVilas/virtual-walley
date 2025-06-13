package com.vw.virtualwallet.configs.security;

import com.vw.virtualwallet.configs.security.filters.EntryPoint;
import com.vw.virtualwallet.configs.security.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenFilter jwtFilter;
    private final EntryPoint entryPoint;

    /**
     * Configures the security filter chain for the application.
     * It sets up CORS, CSRF, session management, and authentication.
     *
     * @param http HttpSecurity object to configure
     * @return SecurityFilterChain object
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->{
                        req.requestMatchers("/v1/auth/**",
                                        "/v2/api-docs",
                                        "v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html",
                                        "/v1/users/create/**").permitAll()
                                .requestMatchers(
                                        "/v1/users/email/**",
                                        "/v1/users/all",
                                        "/v1/users/delete").hasRole("ADMIN")
                                .anyRequest().authenticated();})
                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(eh -> eh.authenticationEntryPoint(entryPoint))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return  http.build();
    }
}
