package com.IslanaCarvalho.UserHub.infrastructure.mapper;
import com.IslanaCarvalho.UserHub.infrastructure.dto.request.CreateUserDTO;
import com.IslanaCarvalho.UserHub.infrastructure.dto.response.UserResponseDTO;
import com.IslanaCarvalho.UserHub.infrastructure.entitys.User;
import jakarta.annotation.Nonnull;

public class UserMapper {
    public static User toEntity(@Nonnull CreateUserDTO dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .password(dto.getPassword())
                .build();
    }

    public static UserResponseDTO toResponseDTO(@Nonnull User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
