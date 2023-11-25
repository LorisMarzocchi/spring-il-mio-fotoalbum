package com.experis.springilmiofotoalbum.api;

import com.experis.springilmiofotoalbum.model.Contact;
import com.experis.springilmiofotoalbum.repository.ContactRepository;
import com.experis.springilmiofotoalbum.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contacts")
@CrossOrigin
public class ContactRestController {
    @Autowired
    private ContactService contactService;
    @Autowired
    private ContactRepository contactRepository;

    @PostMapping
    public Contact createContact(@Valid @RequestBody Contact contact) {
        return contactService.savedContact(contact);
//        contactRepository.save(contact);


    }
}
