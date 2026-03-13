package com.IslanaCarvalho.UserHub.business;

import com.IslanaCarvalho.UserHub.infrastructure.entitys.User;
import com.IslanaCarvalho.UserHub.infrastructure.dto.request.CreateUserDTO;
import com.IslanaCarvalho.UserHub.infrastructure.dto.request.UpdateUserDTO;
import com.IslanaCarvalho.UserHub.infrastructure.dto.response.UserResponseDTO;
import com.IslanaCarvalho.UserHub.infrastructure.exception.EmailAlreadyRegisteredException;
import com.IslanaCarvalho.UserHub.infrastructure.exception.UserNotFoundException;
import com.IslanaCarvalho.UserHub.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.IslanaCarvalho.UserHub.infrastructure.mapper.UserMapper;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO saveUser(CreateUserDTO request) {

        if (repository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyRegisteredException("Email already registered");
        }

        User user = UserMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = repository.save(user);

        return UserMapper.toResponseDTO(savedUser);
    }

    public UserResponseDTO searchUserByEmail(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.toResponseDTO(user);
    }

    public void deleteUserByEmail(String email) {
        if (!repository.existsByEmail(email)) {
            throw new UserNotFoundException("User not found");
        }
        repository.deleteByEmail(email);
    }

    public UserResponseDTO updateUserById(Long id, UpdateUserDTO request) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getName() != null) {
            user.setName(request.getName());
        }

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = repository.save(user);

        return UserMapper.toResponseDTO(updatedUser);
    }
}
