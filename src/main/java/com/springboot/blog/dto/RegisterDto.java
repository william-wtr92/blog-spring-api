package com.springboot.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotEmpty(message = "Name must be not empty or null")
    private String name;

    @NotEmpty(message = "Username must be not empty or null")
    @Size(min = 4, message = "Username must have 4 characters minimum")
    private String username;

    @NotEmpty(message = "Email must be not empty or null")
    @Email(message = "Email must be write with a valid domain and @")
    private String email;

    @NotEmpty(message = "Password must be not empty or null")
    @Size(min = 8, message = "To increase your security, please put a password with 8 characters minimum")
    private String password;
}
