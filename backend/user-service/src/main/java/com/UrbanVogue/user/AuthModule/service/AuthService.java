package com.UrbanVogue.user.AuthModule.service;

import com.UrbanVogue.user.AuthModule.dto.*;
import com.UrbanVogue.user.AuthModule.entity.User;
import com.UrbanVogue.user.AuthModule.jwt.JwtUtil;
import com.UrbanVogue.user.AuthModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;   // for the jwt token generation.

    public AuthResponse register(RegisterRequest request) {

        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()){
            return new AuthResponse("Email already registered",null);
        }

        User user = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhoneNumber(),
                request.getAddress()
        );

        userRepository.save(user);

        return new AuthResponse("User registered successfully",null);
    }

    public AuthResponse login(LoginRequest request) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if(user.isEmpty()){
            return new AuthResponse("User not found",null);
        }

        if(!user.get().getPassword().equals(request.getPassword())){
            return new AuthResponse("Invalid password",null);
        }

       // String token = JwtUtil.generateToken(user.get().getEmail());
        String token = jwtUtil.generateToken(user.get().getEmail());
        return new AuthResponse("Login successful", token);
    }
}



