package com.IslanaCarvalho.UserHub.controller;

import com.IslanaCarvalho.UserHub.business.UserService;
import com.IslanaCarvalho.UserHub.infrastructure.dto.request.UpdateUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.IslanaCarvalho.UserHub.infrastructure.dto.request.CreateUserDTO;
import com.IslanaCarvalho.UserHub.infrastructure.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody @Valid CreateUserDTO request
    ) {
        return ResponseEntity.ok(userService.saveUser(request));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> searchUserByEmail(
            @PathVariable String email
    ) {
        UserResponseDTO user = userService.searchUserByEmail(email);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/")
    public String home() {
        return "API UserHub funcionando 🚀";
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(
            @PathVariable Long id,
            @RequestBody @Valid UpdateUserDTO request
    ){

        UserResponseDTO user = userService.updateUserById (id, request);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<Void> deleteUserByEmail(
            @PathVariable String email
    ) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
