package com.experis.springilmiofotoalbum.service;

import com.experis.springilmiofotoalbum.model.Contact;
import com.experis.springilmiofotoalbum.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact savedContact(Contact contact) {
        contact.setId(null);
        try {
            return contactRepository.save(contact);
        } catch (RuntimeException e) {
            throw new RuntimeException("errore durante il salvataggio" + e.getMessage());
        }

    }
}
