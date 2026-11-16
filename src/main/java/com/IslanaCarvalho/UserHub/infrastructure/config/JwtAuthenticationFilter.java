package com.IslanaCarvalho.UserHub.infrastructure.config;

import com.IslanaCarvalho.UserHub.infrastructure.entitys.User;
import com.IslanaCarvalho.UserHub.infrastructure.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if (jwtService.isTokenValid(token)) {
                User user = userRepository.findByEmail(jwtService.extractEmail(token)).orElse(null);
                if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails details = new org.springframework.security.core.userdetails.User(
                            user.getEmail(), user.getPassword(), new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities()));
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
