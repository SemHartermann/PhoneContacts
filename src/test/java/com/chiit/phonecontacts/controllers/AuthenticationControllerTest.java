package com.chiit.phonecontacts.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chiit.phonecontacts.PhoneContactsApplication;
import com.chiit.phonecontacts.configs.SecurityConfiguration;
import com.chiit.phonecontacts.dtos.requests.AuthenticationRequest;
import com.chiit.phonecontacts.dtos.requests.RegisterRequest;
import com.chiit.phonecontacts.dtos.responses.AuthenticationResponse;
import com.chiit.phonecontacts.entities.Role;
import com.chiit.phonecontacts.entities.User;
import com.chiit.phonecontacts.filters.JwtAuthenticationFilter;
import com.chiit.phonecontacts.repositories.UserRepository;
import com.chiit.phonecontacts.services.AuthenticationService;
import com.chiit.phonecontacts.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private AuthorizationFilter authorizationFilter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void registerReturnStatusOk() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .login("test_user")
                .password("test_password")
                .build();

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                        .token("eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU")
                        .build();


        when(authenticationService.register(registerRequest)).thenReturn(authenticationResponse);

        mockMvc.perform(post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"test_user\"," +
                                "\"password\": \"test_password\"}"))
                        .andExpect(status().isOk());

    }

    @Test
    void registerReturnStatusBadRequest() throws Exception {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .login("test_user")
                .password("test_password")
                .build();

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token("eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU")
                .build();


        when(authenticationService.register(registerRequest)).thenReturn(authenticationResponse);

        mockMvc.perform(post("/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"\"," +
                                "\"password\": \"\"}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void authenticationReturnStatusOk() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .login("test_user")
                .password("test_password")
                .build();

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token("eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU")
                .build();


        when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);

        mockMvc.perform(post("/auth")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"test_user\"," +
                                "\"password\": \"test_password\"}"))
                .andExpect(status().isOk());

    }

    @Test
    void authenticationReturnStatusBadRequest() throws Exception {
        AuthenticationRequest authenticationRequest = AuthenticationRequest.builder()
                .login("test_user")
                .password("test_password")
                .build();

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .token("eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU")
                .build();


        when(authenticationService.authenticate(authenticationRequest)).thenReturn(authenticationResponse);

        mockMvc.perform(post("/auth")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"\"," +
                                "\"password\": \"test_password\"}"))
                .andExpect(status().isBadRequest());

    }

}