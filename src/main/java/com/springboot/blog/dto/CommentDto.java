package com.springboot.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @NotEmpty(message = "Email should not be null or empty")
    @Email(message = "Email must be write with a valid domain and @")
    private String email;

    @NotEmpty(message = "Body should not be null or empty")
    @Size(min = 10, message = "Comment body should have at least 10 characters")
    private String body;
}
