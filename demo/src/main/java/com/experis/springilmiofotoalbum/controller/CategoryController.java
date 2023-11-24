package com.experis.springilmiofotoalbum.controller;

import com.experis.springilmiofotoalbum.exception.CategoryNameUniqueException;
import com.experis.springilmiofotoalbum.model.Category;
import com.experis.springilmiofotoalbum.model.Photo;
import com.experis.springilmiofotoalbum.repository.CategoryRepository;
import com.experis.springilmiofotoalbum.repository.PhotoRepository;
import com.experis.springilmiofotoalbum.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PhotoRepository photoRepository;

    @GetMapping
    public String index(Model model) {
        // passa al model categoryList con la lista di categorie
        model.addAttribute("categoryList", categoryService.getAll());
        // passa al model un category vuoto come attributo categoryObj del form
        model.addAttribute("categoryObj", new Category());
        return "categories/index";
    }

    @PostMapping
    public String doSave(@Valid @ModelAttribute("categoryObj") Category formCategory,
                         BindingResult bindingResult,
                         Model model) {
        // valido la categoria
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryService.getAll());
            return "categories/index";
        }
        try {
            // salvo la nuova categoria su database
            categoryService.save(formCategory);
            return "redirect:/categories";
        } catch (CategoryNameUniqueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A category with name " + e.getMessage() + " already exists");
        }
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Category categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found"));

        // Rimuovere l'ingrediente da ogni pizza associata
        for (Photo photo : categoryToDelete.getPhotos()) {
            photo.getCategories().remove(categoryToDelete);
            // Salva la pizza aggiornata
            photoRepository.save(photo);
        }

        // Ora è sicuro eliminare l'ingrediente
        photoRepository.deleteById(id);
        return "redirect:/categories";
    }
}

