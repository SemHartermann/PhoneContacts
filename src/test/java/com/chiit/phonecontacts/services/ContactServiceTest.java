package com.chiit.phonecontacts.services;

import com.chiit.phonecontacts.dtos.requests.ContactRequest;
import com.chiit.phonecontacts.dtos.requests.RegisterRequest;
import com.chiit.phonecontacts.entities.Contact;
import com.chiit.phonecontacts.entities.User;
import com.chiit.phonecontacts.exceptions.ContactNotFoundException;
import com.chiit.phonecontacts.repositories.ContactRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;
    @InjectMocks
    private ContactService contactService;


    @Test
    void addContactReturnContact() {

        Set<String> emails = new HashSet<>();
        emails.add("testemail@mail.com");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380660335235");

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

        when(contactRepository.save(any())).thenReturn(contact);

        assertEquals(contactService.addContact(user, request), contact);
    }

    @Test
    void deleteContactThrowException() {

        Set<String> emails = new HashSet<>();
        emails.add("testemail@mail.com");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380660335235");

        User user = User.builder()
                .id(30L)
                .login("test_login")
                .password("adsgfafgafg1241234dsfsdf451235dsfgsdg")
                .build();

        Long id = 1L;

        assertThatThrownBy(()->contactService.deleteContact(user, id))
                .isInstanceOf(ContactNotFoundException.class)
                .hasMessageContaining("Contact not found with id: " + id);
    }

    @Test
    void getContactsReturnContacts() throws ContactNotFoundException {

        Set<String> emails = new HashSet<>();
        emails.add("testemail@mail.com");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380660335235");

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

        List<Contact> listContact = new ArrayList<>();
        listContact.add(contact);

        when(contactRepository.findAllByUser(any())).thenReturn(Optional.of(listContact));

        assertEquals(contactService.getContacts(user), listContact);
    }



    @Test
    void getContactsThrowException(){

        Set<String> emails = new HashSet<>();
        emails.add("testemail@mail.com");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380660335235");

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


        assertThatThrownBy(()->contactService.getContacts(user))
                .isInstanceOf(ContactNotFoundException.class)
                .hasMessageContaining("Contact not found with user: " + user.getLogin());
    }

    @Test
    void editContactReturnContact() throws ContactNotFoundException {
        Set<String> emails = new HashSet<>();
        emails.add("testemail@mail.com");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380660335235");

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

        when(contactRepository.findAllByUserAndId(any(),any())).thenReturn(Optional.of(contact));

        when(contactRepository.save(any())).thenReturn(contact);

        assertEquals(contactService.editContact(user, 1L, request), contact);
    }

    @Test
    void editContactThrowException(){
        Set<String> emails = new HashSet<>();
        emails.add("testemail@mail.com");

        Set<String> phoneNumbers = new HashSet<>();
        phoneNumbers.add("+380660335235");

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

        Long id = 1L;

        assertThatThrownBy(()->contactService.editContact(user, id, request))
                .isInstanceOf(ContactNotFoundException.class)
                .hasMessageContaining("Contact not found with id: " + id);
    }
}