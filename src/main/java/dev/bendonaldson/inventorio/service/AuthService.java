package dev.bendonaldson.inventorio.service;

import dev.bendonaldson.inventorio.dto.AuthResponseDto;
import dev.bendonaldson.inventorio.dto.LoginRequestDto;
import dev.bendonaldson.inventorio.dto.RegisterRequestDto;
import dev.bendonaldson.inventorio.exception.UserAlreadyExistsException;
import dev.bendonaldson.inventorio.model.Role;
import dev.bendonaldson.inventorio.model.User;
import dev.bendonaldson.inventorio.repository.UserRepository;
import dev.bendonaldson.inventorio.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new UserAlreadyExistsException("Username '" + request.username() + "' is already taken.");
        }

        var user = new User(
                request.username(),
                passwordEncoder.encode(request.password()),
                Role.USER
        );
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponseDto(jwtToken);
    }

    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User '" + request.username() + "' not found."));
        var jwtToken = jwtService.generateToken(user);

        return new AuthResponseDto(jwtToken);
    }
}
