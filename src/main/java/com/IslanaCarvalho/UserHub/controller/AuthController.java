package com.IslanaCarvalho.UserHub.controller;

import com.IslanaCarvalho.UserHub.business.AuthService;
import com.IslanaCarvalho.UserHub.infrastructure.dto.request.LoginRequestDTO;
import com.IslanaCarvalho.UserHub.infrastructure.dto.response.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        return ResponseEntity.ok(new LoginResponseDTO(authService.login(request)));
    }
}
