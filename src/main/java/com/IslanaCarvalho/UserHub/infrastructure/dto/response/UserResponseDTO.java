package com.IslanaCarvalho.UserHub.infrastructure.dto.response;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String email;
    private String name;
}
