package com.uit.bookstore.controller;

import com.uit.bookstore.dto.ApiResponse;
import com.uit.bookstore.dto.request.AuthenticationRequest;
import com.uit.bookstore.dto.response.AuthenticationResponse;
import com.uit.bookstore.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        log.info("Authentication request for user: {}", request.getUsername());
        var result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
}
