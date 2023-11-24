package com.experis.springilmiofotoalbum.controller;

import com.experis.springilmiofotoalbum.model.User;
import com.experis.springilmiofotoalbum.repository.UserRepository;
import com.experis.springilmiofotoalbum.security.DatabaseUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String index(Authentication authentication, Model model) {
        DatabaseUserDetails principal = (DatabaseUserDetails) authentication.getPrincipal();
        User loggedUser = userRepository.findById(principal.getId()).get();
        model.addAttribute(loggedUser.getFirstName());
        model.addAttribute(loggedUser.getLastName());
//        List<User> users = userRepository.findByRolesName("USER");
//        model.addAttribute("users", users);
//        List<User> users = userRepository.findAll();
//        model.addAttribute("users", users);
        // recupero la lista di users e la passo al model
        return "users/index";
    }
}
