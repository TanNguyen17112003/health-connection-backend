package com.example.health_connection.services;

import org.springframework.stereotype.Service;

import com.example.health_connection.dtos.JwtResponseDto;
import com.example.health_connection.dtos.SigninDto;
import com.example.health_connection.dtos.UserResponseDto;
import com.example.health_connection.exceptions.InvalidCredentialsException;
import com.example.health_connection.models.User;
import com.example.health_connection.respositories.UserRepository;
import com.example.health_connection.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class AuthService{
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(""));
    }
    public JwtResponseDto signIn(SigninDto request) {
        try {
            System.out.println(request.getEmail());
            User user = loadUserByUsername(request.getEmail());
            if ( !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new InvalidCredentialsException();
            }
            UserResponseDto userResponse = new UserResponseDto(user.getUser_id(), user.getEmail(), user.getRole(), user.getFullName(), user.getUsername(), user.getPhone_Number(), user.getGender());
            JwtResponseDto userWithToken = new JwtResponseDto(
                    jwtTokenProvider.generateToken(user),
                    userResponse
            );
            return userWithToken;
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new InvalidCredentialsException();
        }
    }
}
