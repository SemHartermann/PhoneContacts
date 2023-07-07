package com.chiit.phonecontacts.services;

import com.chiit.phonecontacts.dtos.requests.AuthenticationRequest;
import com.chiit.phonecontacts.dtos.requests.RegisterRequest;
import com.chiit.phonecontacts.dtos.responses.AuthenticationResponse;
import com.chiit.phonecontacts.entities.Role;
import com.chiit.phonecontacts.entities.User;
import com.chiit.phonecontacts.exceptions.UserNotFoundException;
import com.chiit.phonecontacts.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void registerReturnAuthenticationResponseWithToken(){
        RegisterRequest registerRequest = RegisterRequest.builder()
                .login("test_user")
                .password("test_password")
                .build();

        when(userRepository.save(any())).thenReturn(Optional.of(new User()).get());
        when(jwtService.generateToken(any())).thenReturn("eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU");

        AuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);

        assertNotNull(authenticationResponse);
        assertEquals(authenticationResponse.getToken(),"eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU");
    }

    @Test
    public void authenticateThrowException(){
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .login("test_user")
                .password("test_password")
                .build();

        assertThatThrownBy(() -> authenticationService.authenticate(authenticationRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with login: " + authenticationRequest.getLogin());
    }

    @Test
    public void authenticationReturnAuthenticationResponseWithToken() throws UserNotFoundException {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .login("test_user")
                .password("test_password")
                .build();

        when(authenticationManager.authenticate(any())).thenReturn(new UsernamePasswordAuthenticationToken(
                "test_user",
                "test_password"));
        when(userRepository.findByLogin(any())).thenReturn(Optional.of(new User()));
        when(jwtService.generateToken(any())).thenReturn("adsgfafgafg1241234dsfsdf451235dsfgsdg");

        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);

        assertNotNull(authenticationResponse);
        assertEquals(authenticationResponse.getToken(),"adsgfafgafg1241234dsfsdf451235dsfgsdg");
    }

}