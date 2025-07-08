package org.example.controller;

import org.example.config.JwtService;
import org.example.dto.LoginRequest;
import org.example.dto.LoginResponse;
import org.example.model.MyUserDetailsService;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user")
    public ResponseEntity<?> token(@RequestBody LoginRequest request) {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }
    @PostMapping("/admin")
    public ResponseEntity<?> admin(@RequestBody LoginRequest request) {
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUsername());
        System.out.println(userDetails.getAuthorities().toString());
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword()) || !(userDetails.getAuthorities().toString().equals("[ADMIN]"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(token));
    }

    ///unsafe, only checking if token is valid, can cause problems when db is cleared
    @PostMapping("/login")
    public ResponseEntity<?> checkToken(@RequestBody LoginResponse respone){
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtService.extractUsername(respone.getToken()));
        if(jwtService.isTokenValid(respone.getToken(), userDetails)){
            return new ResponseEntity<>("ACCEPTED", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }
}

