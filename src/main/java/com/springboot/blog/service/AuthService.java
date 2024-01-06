package com.springboot.blog.service;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
