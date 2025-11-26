package com.library.librarymanagement.service;

import com.library.librarymanagement.dto.request.LoginRequest;
import com.library.librarymanagement.dto.request.StaffRegisterRequest;
import com.library.librarymanagement.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse registerStaff(StaffRegisterRequest request);
    AuthResponse login(LoginRequest request);
    boolean existsByUsername(String username);
}
