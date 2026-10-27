package com.IslanaCarvalho.UserHub.infrastructure.dto.response;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {
    private Long id;
    private String email;
    private String name;
    private BigDecimal balance;
}
