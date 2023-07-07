package com.chiit.phonecontacts.controllers;

import com.chiit.phonecontacts.dtos.requests.ContactRequest;
import com.chiit.phonecontacts.entities.Contact;
import com.chiit.phonecontacts.exceptions.ContactNotFoundException;
import com.chiit.phonecontacts.exceptions.NameConflictException;
import com.chiit.phonecontacts.exceptions.UserNotFoundException;
import com.chiit.phonecontacts.services.ContactService;
import com.chiit.phonecontacts.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
@SecurityRequirement(name = "Authorization")
public class ContactController {

    private final ContactService contactService;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Contact> addContact(@RequestBody @Valid ContactRequest request)
            throws UserNotFoundException, NameConflictException {
        var contact = contactService.addContact(
                userService.getUserFromToken(), request
        );
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id)
            throws UserNotFoundException, ContactNotFoundException {
        contactService.deleteContact(
                userService.getUserFromToken(), id);
        return ResponseEntity.ok("Contact is deleted");
    }

    @GetMapping
    public ResponseEntity <List<Contact>> getContacts()
            throws UserNotFoundException, ContactNotFoundException {
        return ResponseEntity.ok(contactService.getContacts(
                userService.getUserFromToken()));
    }

    @PutMapping("/{id}")
    public ResponseEntity <Contact> editContact(@PathVariable Long id, @RequestBody @Valid ContactRequest request)
            throws
            UserNotFoundException
            ,ContactNotFoundException
            ,NameConflictException {
        return ResponseEntity.ok(contactService.editContact(
                userService.getUserFromToken(), id, request));
    }
}
