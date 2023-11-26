package com.experis.springilmiofotoalbum.controller;

import com.experis.springilmiofotoalbum.model.Contact;
import com.experis.springilmiofotoalbum.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/contacts")
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    @GetMapping
    public String index(Model model) {
        List<Contact> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "contacts/list";
    }
}
