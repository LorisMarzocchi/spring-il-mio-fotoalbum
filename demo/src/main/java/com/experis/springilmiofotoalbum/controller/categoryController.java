package com.experis.springilmiofotoalbum.controller;

import com.experis.springilmiofotoalbum.repository.CategoryRepository;
import com.experis.springilmiofotoalbum.repository.PhotoRepository;
import com.experis.springilmiofotoalbum.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class categoryController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PhotoRepository photoRepository;

//    @GetMapping
//    public String index(Model model) {
//        // passa al model categoryList con la lista di categorie
//        model.addAttribute("categoryList", categoryService.getAll());
//        // passa al model un category vuoto come attributo categoryObj del form
//        model.addAttribute("categoryObj", new Category());
//        return "ingredients/ingredientForm";
//    }
}
