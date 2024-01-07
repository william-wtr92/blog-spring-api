package com.springboot.blog.controller;


import com.springboot.blog.dto.JwtAuthResponse;
import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/")
@Tag(
        name = "Rest API for Auth Resource"
)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // We can use multiple url binding for the same action, use value = {"whatever", "whatever"}
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@Valid  @RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponse response = new JwtAuthResponse(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = {"register", "signup"})
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
