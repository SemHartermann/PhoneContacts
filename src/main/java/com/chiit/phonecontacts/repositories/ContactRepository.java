package com.chiit.phonecontacts.repositories;

import com.chiit.phonecontacts.entities.Contact;
import com.chiit.phonecontacts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByUserIdAndName(Long userId, String name);

    Optional<List<Contact>> findAllByUser(User user);
}
