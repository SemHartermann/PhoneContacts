package com.chiit.phonecontacts.services;

import com.chiit.phonecontacts.dtos.requests.ContactRequest;
import com.chiit.phonecontacts.entities.Contact;
import com.chiit.phonecontacts.entities.User;
import com.chiit.phonecontacts.exceptions.ContactNotFoundException;
import com.chiit.phonecontacts.exceptions.NameConflictException;
import com.chiit.phonecontacts.repositories.ContactRepository;
import com.chiit.phonecontacts.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public Contact addContact(User user, ContactRequest request) throws NameConflictException {


        var contact = Contact.builder()
                .name(request.getName())
                .emails(request.getEmails())
                .phoneNumbers(request.getPhoneNumbers())
                .user(user)
                .build();

        contactRepository.findAllByUser(user).ifPresent(
                contacts -> contacts.forEach(new Consumer<Contact>() {
                                                 @SneakyThrows
                                                 @Override
                                                 public void accept(Contact c) {
                                                     if(c.getName().equals(contact.getName())){
                                                         throw new NameConflictException("Contact with given name already exists");
                                                     }
                                                 }
                                             }
                )
        );

        return contactRepository.save(contact);
    }

    public void deleteContact(User user, Long id) throws ContactNotFoundException {

        Contact contact = contactRepository
                .findAllByUserAndId(user, id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + id));

        if (Objects.equals(user.getId(), contact.getUser().getId())) {
            contactRepository.deleteById(id);
        }

    }

    public List<Contact> getContacts(User user) throws ContactNotFoundException {

        List<Contact> contactList = contactRepository
                .findAllByUser(user)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with user: " + user.getLogin()));

        return contactList;
    }


    public Contact editContact(User user, Long id, ContactRequest request) throws ContactNotFoundException
            ,NameConflictException {

        contactRepository
                .findAllByUserAndId(user, id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found with id: " + id));

        var contact = Contact.builder()
                .id(id)
                .name(request.getName())
                .emails(request.getEmails())
                .phoneNumbers(request.getPhoneNumbers())
                .user(user)
                .build();

        contactRepository.findAllByUser(user).ifPresent(
                contacts -> contacts.forEach(new Consumer<Contact>() {
                                                 @SneakyThrows
                                                 @Override
                                                 public void accept(Contact c) {
                                                     if(c.getName().equals(contact.getName())
                                                     && !c.getId().equals(contact.getId())){
                                                         throw new NameConflictException("Contact with given name already exists");
                                                     }
                                                 }
                                             }
                )
        );

        return contactRepository.save(contact);
    }
}
