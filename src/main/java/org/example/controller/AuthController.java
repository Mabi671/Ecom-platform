package org.example.controller;

import io.jsonwebtoken.JwtException;
import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;
import org.example.model.Role;
import org.example.service.JwtService;
import org.example.service.MyUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          MyUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authenticate(request, false);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> adminLogin(@RequestBody LoginRequest request) {
        return authenticate(request, true);
    }

    @PostMapping("/login")
    public ResponseEntity<String> validateToken(@RequestBody LoginResponse request) {
        try {
            String username = jwtService.extractUsername(request.token());
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(request.token(), userDetails)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("ACCEPTED");
            }
        } catch (JwtException | UsernameNotFoundException | IllegalArgumentException ignored) {
            // Malformed, expired or orphaned tokens are simply not valid.
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }

    private ResponseEntity<?> authenticate(LoginRequest request, boolean requireAdmin) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        } catch (AuthenticationException ex) {
            return invalidCredentials();
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> Role.ADMIN.name().equals(authority.getAuthority()));
        if (requireAdmin && !isAdmin) {
            return invalidCredentials();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new LoginResponse(jwtService.generateToken(userDetails)));
    }

    private ResponseEntity<String> invalidCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
