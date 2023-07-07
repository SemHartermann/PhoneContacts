package com.chiit.phonecontacts.services;

import com.chiit.phonecontacts.exceptions.ContactNotFoundException;
import com.chiit.phonecontacts.exceptions.UserNotFoundException;
import com.chiit.phonecontacts.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @MockBean
    private UserDetails userDetails;
    @InjectMocks
    private UserService userService;

    @Test
    void getUserFromTokenThrowException() throws UserNotFoundException {

        assertThatThrownBy(()->userService.getUserFromToken("Beare ayJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with login: " + null);

    }
}