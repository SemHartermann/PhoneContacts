package com.chiit.phonecontacts.services;

import com.chiit.phonecontacts.entities.User;
import com.chiit.phonecontacts.exceptions.UserNotFoundException;
import com.chiit.phonecontacts.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public User getUserFromToken() throws UserNotFoundException {

        String authHeader = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");

        String token = authHeader.split(" ")[1].trim();


        User user = userRepository
                .findByLogin(jwtService.extractUsername(token))
                .orElseThrow(() -> new UserNotFoundException("User not found with login: "
                        + jwtService.extractUsername(token)));

        return user;

    }

}
