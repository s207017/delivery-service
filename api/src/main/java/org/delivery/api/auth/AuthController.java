package org.delivery.api.auth;

import lombok.RequiredArgsConstructor;
import org.delivery.api.auth.model.AuthResponse;
import org.delivery.api.auth.model.LoginRequest;
import org.delivery.api.auth.model.SignupRequest;
import org.delivery.api.common.api.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public Api<AuthResponse> signup(@RequestBody @Valid SignupRequest request) {
        return Api.OK(authService.signup(request));
    }

    @PostMapping("/login")
    public Api<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return Api.OK(authService.login(request));
    }
}
