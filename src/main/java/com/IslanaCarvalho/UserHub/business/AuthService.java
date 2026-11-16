package com.IslanaCarvalho.UserHub.business;

import com.IslanaCarvalho.UserHub.infrastructure.config.JwtService;
import com.IslanaCarvalho.UserHub.infrastructure.dto.request.LoginRequestDTO;
import com.IslanaCarvalho.UserHub.infrastructure.exception.InvalidCredentialsException;
import com.IslanaCarvalho.UserHub.infrastructure.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(LoginRequestDTO request) {
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Credenciais inválidas"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Credenciais inválidas");
        }
        return jwtService.generateToken(user.getEmail());
    }
}
