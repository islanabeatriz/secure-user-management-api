package com.IslanaCarvalho.UserHub.infrastructure.dto.request;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDTO {

    @Email
    private String email;

    private String password;

    private String name;
}
