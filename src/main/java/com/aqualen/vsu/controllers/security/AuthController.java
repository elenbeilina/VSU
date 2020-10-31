package com.aqualen.vsu.controllers.security;

import com.aqualen.vsu.config.jwt.JwtProvider;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.jdo.AuthRequest;
import com.aqualen.vsu.jdo.AuthResponse;
import com.aqualen.vsu.jdo.RegistrationRequest;
import com.aqualen.vsu.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public void registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        userService.add(registrationRequest);
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
        User userEntity = userService.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
