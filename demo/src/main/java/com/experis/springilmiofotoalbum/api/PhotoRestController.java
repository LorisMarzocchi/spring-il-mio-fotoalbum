package com.experis.springilmiofotoalbum.api;

import com.experis.springilmiofotoalbum.exception.PhotoNotFoundException;
import com.experis.springilmiofotoalbum.model.Photo;
import com.experis.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/photos")
@CrossOrigin
public class PhotoRestController {
    @Autowired
    private PhotoService photoService;

    @GetMapping
    public List<Photo> index(@RequestParam Optional<String> search) {
        return photoService.getVisibleFotoList(search);
    }
//    @GetMapping
//    public String index(@RequestParam Optional<String> search, Model model) {
//        model.addAttribute("fotoList", photoService.getVisibleFotoList(search));
//        model.addAttribute("searchKeyword", search.orElse(""));
//        return "photos/index";
//    }

    @GetMapping("/{id}")
    public Photo show(@PathVariable Integer id) {
        try {
            // se trovo la foto con id mostro i dettagli
            return photoService.getPhotoById(id);
        } catch (PhotoNotFoundException e) {
            // tiro un eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con id: " + id + " non è stata trovata!");
        }
    }
}
