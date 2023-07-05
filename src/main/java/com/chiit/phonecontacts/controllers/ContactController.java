package com.chiit.phonecontacts.controllers;

import com.chiit.phonecontacts.dtos.requests.ContactRequest;
import com.chiit.phonecontacts.entities.Contact;
import com.chiit.phonecontacts.exceptions.ContactNotFoundException;
import com.chiit.phonecontacts.exceptions.UserNotFoundException;
import com.chiit.phonecontacts.services.ContactService;
import com.chiit.phonecontacts.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestHeader("Authorization") String authHeader
            ,@RequestBody @Valid ContactRequest request) throws UserNotFoundException {
        var contact = contactService.addContact(
                userService.getUserFromToken(authHeader),
                request
        );
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@RequestHeader("Authorization") String authHeader
            ,@PathVariable Long id) throws UserNotFoundException, ContactNotFoundException {
        contactService.deleteContact(
                userService.getUserFromToken(authHeader),
                id
        );
        return ResponseEntity.ok("Contact is deleted");
    }

    @GetMapping
    public ResponseEntity <List<Contact>> getContacts(@RequestHeader("Authorization") String authHeader)
            throws UserNotFoundException, ContactNotFoundException {
        return ResponseEntity.ok(contactService.getContacts(
                userService.getUserFromToken(authHeader)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity <Contact> editContact(@RequestHeader("Authorization") String authHeader
            ,@PathVariable Long id
            ,@RequestBody @Valid ContactRequest request) throws UserNotFoundException, ContactNotFoundException {
        return ResponseEntity.ok(contactService.editContact(
                userService.getUserFromToken(authHeader),
                id,
                request
        ));
    }
}
