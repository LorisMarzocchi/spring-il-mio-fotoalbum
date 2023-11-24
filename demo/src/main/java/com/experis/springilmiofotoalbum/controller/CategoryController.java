package com.experis.springilmiofotoalbum.controller;

import com.experis.springilmiofotoalbum.exception.CategoryNameUniqueException;
import com.experis.springilmiofotoalbum.exception.CategoryNotFoundException;
import com.experis.springilmiofotoalbum.model.Category;
import com.experis.springilmiofotoalbum.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    //    @Autowired
//    CategoryRepository categoryRepository;
//    @Autowired
//    PhotoRepository photoRepository;

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
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            // Il servizio gestirà la logica di eliminazione
            categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("message", "Categoria eliminata con successo");
            return "redirect:/categories";
        } catch (CategoryNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}

