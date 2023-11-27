package com.experis.springilmiofotoalbum.controller;

import com.experis.springilmiofotoalbum.exception.PhotoNotFoundException;
import com.experis.springilmiofotoalbum.model.Photo;
import com.experis.springilmiofotoalbum.model.User;
import com.experis.springilmiofotoalbum.service.CategoryService;
import com.experis.springilmiofotoalbum.service.PhotoService;
import com.experis.springilmiofotoalbum.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Optional<String> search, Model model) {
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();

        List<Photo> photoList;
        if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("SUPER_ADMIN"))) {
            photoList = photoService.getAllPhotos(search);
        } else {
            photoList = photoService.getPhotosByUser(currentUser, search);
        }
//        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("photoList", photoList);
        return "photos/list";
    }

//    @GetMapping
//    public String index(@RequestParam Optional<String> search, Model model) {
//        List<Photo> photoList = photoService.getPhotoSearch(search);
//        model.addAttribute("photoList", photoList);
//        return "photos/list";
//    }

//    @GetMapping
//    public String index(@AuthenticationPrincipal DatabaseUserDetails userDetails, @RequestParam Optional<String> search, Model model) {
//        User currentUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
//        List<Photo> photoList;
//
//        if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("SUPER_ADMIN"))) {
//            photoList = photoService.getAllPhotos(); // Metodo da implementare nel PhotoService
//        } else {
//            photoList = photoService.getPhotosByUser(currentUser); // Metodo da implementare nel PhotoService
//        }
//
//        model.addAttribute("photoList", photoList);
//        return "photos/list";
//    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        try {
            Photo photo = photoService.getPhotoById(id);
            model.addAttribute("photo", photo);
            return "photos/show";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("photo", new Photo());

//        List<Category> categoryList = categoryService.getAll();
        model.addAttribute("categoryList", categoryService.getAll());
        return "photos/form";
    }

    @PostMapping("/store")
    public String store(@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryService.getAll());
            return "photos/form";
        }
        try {
            // Trova l'utente autenticato e collega la foto a quell'utente
            User user = userService.findByEmail(userDetails.getUsername()).orElseThrow();

            /// Con overload del metodo save in PhotoService -->
            /// photo.setId(null);
            /// formPhoto.setUser(user);
            /// photoService.savePhoto(formPhoto);

            Photo savedPhoto = photoService.savePhoto(formPhoto, user);
            return "redirect:/photos/show/" + savedPhoto.getId();
        } catch (RuntimeException e) {
            bindingResult.addError(new FieldError("photo", "titolo", e.getMessage(), false, null, null, "Il nome deve essere unico"));
            return "photos/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Photo photo = photoService.getPhotoById(id);
        model.addAttribute("photo", photo);
        model.addAttribute("categoryList", categoryService.getAll());
        return "photos/form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryService.getAll());
            return "photos/form";
        }
        try {
            Photo updatedPhoto = photoService.updatePhoto(formPhoto);
            return "redirect:/photos/show/" + updatedPhoto.getId();

        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Photo deletedPhoto = photoService.getPhotoById(id);
            photoService.deletePhoto(id);
            redirectAttributes.addFlashAttribute("message", "Photo " + deletedPhoto.getTitolo() + " deleted");
            return "redirect:/photos";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // visibilità
    @PostMapping("/toggleVisibility/{id}")
    public String toggleVisibility(@PathVariable Integer id, Model model) {
        try {
            Photo photo = photoService.getPhotoById(id);
            photo.setVisible(!photo.isVisible());
            photoService.updatePhoto(photo);

            return "redirect:/photos";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con id: " + id + " non è stata trovata!");
        }
    }

}
