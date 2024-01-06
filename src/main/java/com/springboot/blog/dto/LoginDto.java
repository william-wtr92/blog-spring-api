package com.springboot.blog.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotEmpty(message = "Username or email must be not empty or null")
    private String usernameOrEmail;
    @NotEmpty(message = "Password must be not empty or null")
    private String password;
}
