package com.chiit.phonecontacts.controllers;

import com.chiit.phonecontacts.dtos.requests.AuthenticationRequest;
import com.chiit.phonecontacts.dtos.responses.AuthenticationResponse;
import com.chiit.phonecontacts.dtos.requests.RegisterRequest;
import com.chiit.phonecontacts.exceptions.UserNotFoundException;
import com.chiit.phonecontacts.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request)
            throws UserNotFoundException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
