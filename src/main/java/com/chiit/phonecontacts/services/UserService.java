package com.chiit.phonecontacts.services;

import com.chiit.phonecontacts.entities.User;
import com.chiit.phonecontacts.exceptions.UserNotFoundException;
import com.chiit.phonecontacts.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public User getUserFromToken(String authHeader) throws UserNotFoundException {

        String token = authHeader.split(" ")[1].trim();

        User user = userRepository
                .findByLogin(jwtService.extractUsername(token))
                .orElseThrow(() -> new UserNotFoundException("User not found with login: "
                        + jwtService.extractUsername(token)));

        return user;

    }

}
