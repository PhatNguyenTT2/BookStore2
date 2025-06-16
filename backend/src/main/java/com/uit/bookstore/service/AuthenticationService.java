package com.uit.bookstore.service;

import com.uit.bookstore.dto.request.AuthenticationRequest;
import com.uit.bookstore.dto.response.AuthenticationResponse;
import com.uit.bookstore.entity.User;
import com.uit.bookstore.enums.ErrorCode;
import com.uit.bookstore.exception.AppException;
import com.uit.bookstore.repository.UserRepository;
import com.uit.bookstore.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            // Check if user exists
            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            // Authenticate
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // Generate token
            String token = jwtUtils.generateJwtToken(authentication);

            return AuthenticationResponse.builder()
                    .token(token)
                    .authenticated(true)
                    .build();

        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", request.getUsername());
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
