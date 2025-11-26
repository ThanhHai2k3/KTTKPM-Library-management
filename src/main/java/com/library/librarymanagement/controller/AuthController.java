package com.library.librarymanagement.controller;

import com.library.librarymanagement.dto.request.LoginRequest;
import com.library.librarymanagement.dto.request.StaffRegisterRequest;
import com.library.librarymanagement.dto.response.AuthResponse;
import com.library.librarymanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/staff")
    public ResponseEntity<AuthResponse> registerStaff(@RequestBody StaffRegisterRequest request) {
        AuthResponse response = authService.registerStaff(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> check(@RequestParam String username) {
        return ResponseEntity.ok(authService.existsByUsername(username));
    }
}
