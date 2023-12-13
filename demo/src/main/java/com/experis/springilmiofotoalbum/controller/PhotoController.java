package com.experis.springilmiofotoalbum.controller;

import com.experis.springilmiofotoalbum.dto.PhotoDto;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/photos")
public class PhotoController {

    // Iniezione dei servizi
    @Autowired
    private PhotoService photoService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;


    /**
     * Metodo per visualizzare la pagina principale con l'elenco delle foto.
     * - @AuthenticationPrincipal per ottenere i dettagli dell'utente autenticato.
     *
     * @param userDetails Dettagli dell'utente autenticato.
     * @param search      Parametro di ricerca opzionale.
     * @param model       Modello per passare dati alla view.
     * @return Nome della view da visualizzare.
     */
    @GetMapping
    public String index(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Optional<String> search, Model model) {
        // Recupera l'utente corrente tramite le sue credenziali
        User currentUser = userService.findByEmail(userDetails.getUsername()).orElseThrow();
        // Ottiene la lista di tutti gli utenti
        List<User> userList = userService.getAllUsers();
        List<Photo> photoList;
        // Ottiene la lista delle foto in base al ruolo dell'utente (tutte le foto per i SUPER_ADMIN)
        if (currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("SUPER_ADMIN"))) {
            photoList = photoService.getAllPhotos(search);
        } else {
            photoList = photoService.getPhotosByUser(currentUser, search);
        }
        // Aggiunge gli attributi al modello per essere visualizzati nella view
        model.addAttribute("userList", userList);
        model.addAttribute("photoList", photoList);
        return "photos/list";
    }


    /**
     * Metodo per mostrare i dettagli di una specifica foto.
     *
     * @param id    ID della foto.
     * @param model Modello per passare dati alla view.
     * @return Nome della view da visualizzare.
     */
    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        try {
            // Recupera la foto dal servizio tramite il suo ID
            Photo photo = photoService.getPhotoById(id);
            // Aggiunge la foto al modello
            model.addAttribute("photo", photo);
            return "photos/show";
        } catch (PhotoNotFoundException e) {
            // Gestisce il caso in cui la foto non viene trovata
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Metodo per preparare la view per la creazione di una nuova foto
    @GetMapping("/create")
    public String create(Model model) {
        // Aggiunge un nuovo PhotoDto vuoto al modello per la creazione di una foto
        model.addAttribute("photo", new PhotoDto());
//        List<Category> categoryList = categoryService.getAll();
        // Aggiunge la lista delle categorie al modello
        model.addAttribute("categoryList", categoryService.getAll());
        return "photos/form";
    }


    /**
     * Metodo per gestire il salvataggio di una nuova foto
     *
     * @param userDetails   Dettagli dell'utente corrente.
     * @param formPhoto     Dati della foto dal form.
     * @param bindingResult Risultato della validazione.
     * @param model         Modello per passare dati alla view.
     * @return Nome della view.
     */
    @PostMapping("/store")
    public String store(@AuthenticationPrincipal UserDetails userDetails, @Valid @ModelAttribute("photo") PhotoDto formPhoto, BindingResult bindingResult, Model model) {
        // Controlla se ci sono errori di validazione
        if (bindingResult.hasErrors()) {
            // In caso di errori, ritorna alla stessa view con i messaggi di errore
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

            // Salva la foto tramite il servizio
            Photo savedPhoto = photoService.savePhoto(formPhoto, user);
            // Reindirizza alla pagina di dettaglio della foto appena creata
            return "redirect:/photos/show/" + savedPhoto.getId();
        } catch (RuntimeException e) {
            bindingResult.addError(new FieldError("photo", "titolo", e.getMessage(), false, null, null, "Il nome deve essere unico"));
            return "photos/form";
        } catch (IOException e) {
            // Gestisce eventuali eccezioni durante il salvataggio della foto
            bindingResult.addError(new FieldError("photo", "covereFile", "impossibile salvare il file"));
            return "photos/form";
        }
    }

    // Metodo per preparare la view per la modifica di una foto esistente
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        try {
//            Photo photo = photoService.getPhotoById(id);
            // Recupera il PhotoDto per l'ID specificato
            model.addAttribute("photo", photoService.getPhotoDtoById(id));
            // Aggiunge la lista delle categorie al modello
            model.addAttribute("categoryList", categoryService.getAll());
            return "photos/form";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Metodo per aggiornare una foto esistente
    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("photo") PhotoDto formPhoto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // In caso di errori di validazione, ritorna alla view di modifica
            model.addAttribute("categoryList", categoryService.getAll());
            return "photos/form";
        }
        try {
            // Aggiorna la foto tramite il servizio e reindirizza alla sua pagina di dettaglio
            Photo updatedPhoto = photoService.updatePhoto(formPhoto);
            return "redirect:/photos/show/" + updatedPhoto.getId();

        } catch (PhotoNotFoundException e) {
            // Gestisce eventuali eccezioni durante l'aggiornamento
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IOException e) {
            bindingResult.addError(new FieldError("photo", "coverFile", null, false, null, null,
                    "Unable to save file"));
            return "photos/form";
        }
    }

    // Metodo per eliminare una foto
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            // Elimina la foto e aggiunge un messaggio di conferma
            Photo deletedPhoto = photoService.getPhotoById(id);
            photoService.deletePhoto(id);
            redirectAttributes.addFlashAttribute("message", "Photo " + deletedPhoto.getTitolo() + " deleted");
            return "redirect:/photos";
        } catch (PhotoNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // visibilità
    // Metodo per cambiare la visibilità di una foto
    @PostMapping("/toggleVisibility/{id}")
    public String toggleVisibility(@PathVariable Integer id, Model model) {
        try {
            // Cambia la visibilità della foto e la aggiorna tramite il servizio
            Photo photo = photoService.getPhotoById(id);
            photo.setVisible(!photo.isVisible());
            photoService.updatePhoto(photo);

            return "redirect:/photos";
        } catch (PhotoNotFoundException e) {
            // Gestisce il caso in cui la foto da modificare non viene trovata
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "La foto con id: " + id + " non è stata trovata!");
        }
    }

}
