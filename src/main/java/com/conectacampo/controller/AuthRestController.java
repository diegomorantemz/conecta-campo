package com.conectacampo.controller;

import com.conectacampo.model.User;
import com.conectacampo.repository.UserRepository;
import com.conectacampo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String email,
                                                     @RequestParam String password) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            if (authentication.isAuthenticated()) {
                User user = userRepository.findByEmail(email).orElseThrow();
                String token = jwtTokenProvider.generateToken(email, user.getRole().name());

                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("role", user.getRole().name());
                response.put("email", email);
                response.put("message", "Login exitoso");

                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
        }

        return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body(Map.of("error", "Token no proporcionado"));
        }

        String token = authHeader.substring(7);
        if (jwtTokenProvider.validateToken(token) && !jwtTokenProvider.isTokenExpired(token)) {
            String email = jwtTokenProvider.getEmailFromToken(token);
            String role = jwtTokenProvider.getRoleFromToken(token);

            Map<String, String> response = new HashMap<>();
            response.put("email", email);
            response.put("role", role);
            response.put("message", "Token válido");
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(Map.of("error", "Token inválido o expirado"));
    }
}