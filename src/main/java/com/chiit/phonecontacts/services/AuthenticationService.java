package com.chiit.phonecontacts.services;

import com.chiit.phonecontacts.dtos.requests.AuthenticationRequest;
import com.chiit.phonecontacts.dtos.responses.AuthenticationResponse;
import com.chiit.phonecontacts.dtos.requests.RegisterRequest;
import com.chiit.phonecontacts.entities.Role;
import com.chiit.phonecontacts.entities.User;
import com.chiit.phonecontacts.exceptions.UserNotFoundException;
import com.chiit.phonecontacts.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(()->new UserNotFoundException("User not found with login: " + request.getLogin()));

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();

    }
}
