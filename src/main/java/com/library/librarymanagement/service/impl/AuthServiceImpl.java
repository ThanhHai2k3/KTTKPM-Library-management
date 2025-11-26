package com.library.librarymanagement.service.impl;

import com.library.librarymanagement.dto.request.LoginRequest;
import com.library.librarymanagement.dto.request.StaffRegisterRequest;
import com.library.librarymanagement.dto.response.AuthResponse;
import com.library.librarymanagement.entity.Role;
import com.library.librarymanagement.entity.Staff;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.enums.RoleName;
import com.library.librarymanagement.repository.RoleRepository;
import com.library.librarymanagement.repository.StaffRepository;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.security.JwtService;
import com.library.librarymanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    @Transactional
    public AuthResponse registerStaff(StaffRegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Role staffRole = roleRepository.findByName(RoleName.ROLE_STAFF)
                .orElseThrow(() -> new RuntimeException("Role STAFF not found"));

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User newUser = User.builder()
                .username(request.getUsername())
                .password(hashedPassword)
                .fullName(request.getFullName())
                .role(staffRole)
                .build();

        User savedUser = userRepository.save(newUser);

        Staff staff = Staff.builder()
                .user(savedUser)
                .position(request.getPosition())
                .build();

        staffRepository.save(staff);

        String token = jwtService.generateToken(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getRole().getName().name()
        );

        return new AuthResponse(
                token,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getFullName(),
                savedUser.getRole().getName().name()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        //validate username, password
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRole().getName().name()
        );

        return new AuthResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getRole().getName().name()
        );
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }
}
