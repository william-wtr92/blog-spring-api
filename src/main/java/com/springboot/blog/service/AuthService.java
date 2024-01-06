package com.springboot.blog.service;

import com.springboot.blog.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
