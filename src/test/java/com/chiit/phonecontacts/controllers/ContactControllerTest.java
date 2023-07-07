package com.chiit.phonecontacts.controllers;

import com.chiit.phonecontacts.dtos.requests.ContactRequest;
import com.chiit.phonecontacts.entities.Contact;
import com.chiit.phonecontacts.entities.User;
import com.chiit.phonecontacts.exceptions.ContactNotFoundException;
import com.chiit.phonecontacts.exceptions.UserNotFoundException;
import com.chiit.phonecontacts.filters.JwtAuthenticationFilter;
import com.chiit.phonecontacts.services.ContactService;
import com.chiit.phonecontacts.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
@AutoConfigureMockMvc(addFilters = false)
class ContactControllerTest {

    @MockBean
    private ContactService contactService;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean
    private AuthorizationFilter authorizationFilter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addContactCreatedResponse() throws Exception {

        Set<String> emails = new HashSet<>();
        emails.add("test_email@mail.com");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380950445247");

        ContactRequest request = ContactRequest.builder()
                .name("test_user")
                .emails(emails)
                .phoneNumbers(phoneNumbers)
                .build();

        User user = User.builder()
                .id(30L)
                .login("test_login")
                .password("adsgfafgafg1241234dsfsdf451235dsfgsdg")
                .build();

        var contact = Contact.builder()
                .name(request.getName())
                .emails(request.getEmails())
                .phoneNumbers(request.getPhoneNumbers())
                .user(user)
                .build();

        when(userService.getUserFromToken(any())).thenReturn(user);
        when(contactService.addContact(eq(user), eq(request))).thenReturn(contact);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU");

        mockMvc.perform(post("/contacts")
                        .with(csrf())
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test_user\"," +
                                "\"emails\": [\"test_email@mail.com\"]," +
                                "\"phoneNumbers\": [\"+380950445247\"]}"))
                .andExpect(status().isCreated());

    }

    @Test
    void addContactUserNotFound() throws Exception {

        Set<String> emails = new HashSet<>();
        emails.add("test_email@mail.com");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380950445247");

        ContactRequest request = ContactRequest.builder()
                .name("test_user")
                .emails(emails)
                .phoneNumbers(phoneNumbers)
                .build();

        User user = User.builder()
                .id(30L)
                .login("test_login")
                .password("adsgfafgafg1241234dsfsdf451235dsfgsdg")
                .build();

        var contact = Contact.builder()
                .name(request.getName())
                .emails(request.getEmails())
                .phoneNumbers(request.getPhoneNumbers())
                .user(user)
                .build();

        when(userService.getUserFromToken(any())).thenThrow(new UserNotFoundException(""));
        when(contactService.addContact(eq(user), eq(request))).thenReturn(contact);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU");

        mockMvc.perform(post("/contacts")
                        .with(csrf())
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test_user\"," +
                                "\"emails\": [\"test_email@mail.com\"]," +
                                "\"phoneNumbers\": [\"+380950445247\"]}"))
                .andExpect(status().isNotFound());

    }

    @Test
    void addContactBadRequest() throws Exception {

        Set<String> emails = new HashSet<>();
        emails.add("test_email@mail.com");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380950445247");

        ContactRequest request = ContactRequest.builder()
                .name("test_user")
                .emails(emails)
                .phoneNumbers(phoneNumbers)
                .build();

        User user = User.builder()
                .id(30L)
                .login("test_login")
                .password("adsgfafgafg1241234dsfsdf451235dsfgsdg")
                .build();

        var contact = Contact.builder()
                .name(request.getName())
                .emails(request.getEmails())
                .phoneNumbers(request.getPhoneNumbers())
                .user(user)
                .build();

        when(userService.getUserFromToken(any())).thenReturn(user);
        when(contactService.addContact(eq(user), eq(request))).thenReturn(contact);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU");

        mockMvc.perform(post("/contacts")
                        .with(csrf())
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test_user\"," +
                                "\"emails\": [aa@]," +
                                "\"phoneNumbers\": [+380950445247]}"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deleteContactOkResponse() throws Exception {

        User user = User.builder()
                .id(30L)
                .login("test_login")
                .password("adsgfafgafg1241234dsfsdf451235dsfgsdg")
                .build();


        when(userService.getUserFromToken(any())).thenReturn(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU");

        mockMvc.perform(delete("/contacts/{id}",1L)
                        .with(csrf())
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void getContactsOkResponse() throws Exception {
        Set<String> emails = new HashSet<>();
        emails.add("test_email@mail.com");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380950445247");

        ContactRequest request = ContactRequest.builder()
                .name("test_user")
                .emails(emails)
                .phoneNumbers(phoneNumbers)
                .build();

        var user = User.builder()
                .id(30L)
                .login("test_login")
                .password("adsgfafgafg1241234dsfsdf451235dsfgsdg")
                .build();

        var contact = Contact.builder()
                .name(request.getName())
                .emails(request.getEmails())
                .phoneNumbers(request.getPhoneNumbers())
                .user(user)
                .build();

        List<Contact> listContact = new ArrayList<>();
        listContact.add(contact);

        when(userService.getUserFromToken(any())).thenReturn(user);
        when(contactService.getContacts(eq(user))).thenReturn(listContact);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU");

        mockMvc.perform(get("/contacts")
                        .with(csrf())
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());

    }

    @Test
    void editContactNotFound() throws Exception {
        Set<String> emails = new HashSet<>();
        emails.add("test_email@mail.ua");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380660445247");

        ContactRequest request = ContactRequest.builder()
                .name("test_user_andrew")
                .emails(emails)
                .phoneNumbers(phoneNumbers)
                .build();

        User user = User.builder()
                .id(30L)
                .login("test_login")
                .password("adsgfafgafg1241234dsfsdf451235dsfgsdg")
                .build();

        when(userService.getUserFromToken(any())).thenReturn(user);
        when(contactService.editContact(eq(user), eq(1L), eq(request))).thenThrow(new ContactNotFoundException(""));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU");

        mockMvc.perform(put("/contacts/{id}", 1L)
                        .with(csrf())
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test_user_andrew\"," +
                                "\"emails\": [\"test_email@mail.ua\"]," +
                                "\"phoneNumbers\": [\"+380660445247\"]}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void editContactOkResponse() throws Exception {
        Set<String> emails = new HashSet<>();
        emails.add("test_email@mail.ua");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380660445247");

        ContactRequest request = ContactRequest.builder()
                .name("test_user_andrew")
                .emails(emails)
                .phoneNumbers(phoneNumbers)
                .build();

        User user = User.builder()
                .id(30L)
                .login("test_login")
                .password("adsgfafgafg1241234dsfsdf451235dsfgsdg")
                .build();

        var contact = Contact.builder()
                .name(request.getName())
                .emails(request.getEmails())
                .phoneNumbers(request.getPhoneNumbers())
                .user(user)
                .build();

        when(userService.getUserFromToken(any())).thenReturn(user);
        when(contactService.editContact(eq(user), eq(1L), eq(request))).thenReturn(contact);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU");

        mockMvc.perform(put("/contacts/{id}", 1L)
                        .with(csrf())
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test_user_andrew\"," +
                                "\"emails\": [\"test_email@mail.ua\"]," +
                                "\"phoneNumbers\": [\"+380660445247\"]}"))
                .andExpect(status().isOk());
    }

    @Test
    void editContactBadRequest() throws Exception {
        Set<String> emails = new HashSet<>();
        emails.add("test_email@mail.ua");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380660445247");

        ContactRequest request = ContactRequest.builder()
                .name("test_user_andrew")
                .emails(emails)
                .phoneNumbers(phoneNumbers)
                .build();

        User user = User.builder()
                .id(30L)
                .login("test_login")
                .password("adsgfafgafg1241234dsfsdf451235dsfgsdg")
                .build();

        var contact = Contact.builder()
                .name(request.getName())
                .emails(request.getEmails())
                .phoneNumbers(request.getPhoneNumbers())
                .user(user)
                .build();

        when(userService.getUserFromToken(any())).thenReturn(user);
        when(contactService.editContact(eq(user), eq(1L), eq(request))).thenReturn(contact);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.zyJzdWIiOiJNaXNoYSIsImlhdCI6MTY4ODY3MDAxMywiZXhwIjoxNjg4NzU2NDEzfQ.hYxI6rrIxofsJjliX_B2E6tn9hqT40dtdUNO7gr9dXU");

        mockMvc.perform(put("/contacts/{id}", 1L)
                        .with(csrf())
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test_user_andrew\"," +
                                "\"emails\": [\"test_email@mail.ua\"]," +
                                "\"phoneNumbers\": [\"+38-asdas\"]}"))
                .andExpect(status().isBadRequest());
    }
}