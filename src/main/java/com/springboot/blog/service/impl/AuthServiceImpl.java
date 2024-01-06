package com.springboot.blog.service.impl;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {

        // We call the authenticate() method form authenticationManager and we pass to her email/username & pwd received from DTO
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()
        ));

        // Save into the Spring Security Context the auth of the user grab below
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User successfully log-in !";
    }

    @Override
    public String register(RegisterDto registerDto) {

        // Check if provided username by DTO is already used
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username already use !");
        }

        // Check if provided email by DTO is already used
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email already use !");
        }

        // Creation of new User object and set field with DTO information
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        // Encode the password before send it to Database
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Creation of new Set of Role
        Set<Role> roles = new HashSet<>();
        // Finding ROLE_USER in role table
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        // Add it to the Set
        roles.add(userRole);
        // Put the Set below to the new User
        user.setRoles(roles);

        // Save the new User into de Database
        userRepository.save(user);

        return "User successfully register !";
    }
}
