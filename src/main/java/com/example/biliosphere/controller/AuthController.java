package com.example.biliosphere.controller;

import com.example.biliosphere.model.*;
import com.example.biliosphere.repo.RoleRepository;
import com.example.biliosphere.sequrity.jwt.JwtUtils;
import com.example.biliosphere.sequrity.service.UserDetailImplementation;
import com.example.biliosphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.PasswordAuthentication;
import java.util.Set;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/26/2025 2:01 PM
@Last Modified 1/26/2025 2:01 PM
Version 1.0
*/
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;

    // Constructor untuk dependency injection
    public AuthController(
            AuthenticationManager authenticationManager,
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils,
            RoleRepository roleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest request) {
        try {
            // Validasi input
            if (request.getEmail() == null || request.getEmail().isEmpty() ||
                    request.getPassword() == null || request.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Error: Email or password cannot be empty!");
            }
            // Otentikasi menggunakan email
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            // Set Authentication di SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Generate JWT token
            String token = jwtUtils.generateJwtToken(authentication);
            // Ambil data user dari principal
            UserDetailImplementation principal = (UserDetailImplementation) authentication.getPrincipal();
            // Berikan response dengan token dan informasi user
            JwtResponse jwtResponse = new JwtResponse(
                    token,
                    principal.getUsername(), // Ini bisa tetap menggunakan nama
                    principal.getEmail()
            );
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Error: Invalid email or password!");
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Users> signUp(@RequestBody SignUpRequest request) {
        Users user = new Users();
        user.setNama(request.getNama());
        user.setEmail(request.getEmail());
        user.setAlamat(request.getAlamat());
        user.setNoTelpon(request.getNoTelpon());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Ambil role default
        Roles defaultRole = roleRepository.findByName("Member")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        user.setRoles(Set.of(defaultRole));

        Users savedUser = userService.save(user);
        return ResponseEntity.ok(savedUser);
    }
}