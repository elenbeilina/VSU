package com.aqualen.vsu.controllers.security;

import com.aqualen.vsu.config.jwt.JwtProvider;
import com.aqualen.vsu.dto.RegistrationRequest;
import com.aqualen.vsu.entity.User;
import com.aqualen.vsu.dto.AuthRequest;
import com.aqualen.vsu.dto.AuthResponse;
import com.aqualen.vsu.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("register")
    public void registerUser(@RequestBody RegistrationRequest user) {
        userService.add(user);
    }

    @PostMapping("sign-in")
    public ResponseEntity<AuthResponse> authentication(@RequestBody AuthRequest request) {
        User userEntity = userService.findByUsernameAndPassword(request.getUsername(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getUsername());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}
