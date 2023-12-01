package com.experis.springilmiofotoalbum.db_utils;

import com.experis.springilmiofotoalbum.model.Photo;
import com.experis.springilmiofotoalbum.model.User;
import com.experis.springilmiofotoalbum.repository.PhotoRepository;
import com.experis.springilmiofotoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Collections;

@Component
public class dbSeeder implements CommandLineRunner {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (photoRepository.count() == 0) {
            // Esempio di come leggere un file immagine
            ClassPathResource imgFile = new ClassPathResource("static/images/image_1.png");
            byte[] imageBytes = Files.readAllBytes(imgFile.getFile().toPath());

            // Crea un utente di esempio (assumi che l'ID 1 esista)
            User user = userRepository.findById(1).orElse(null);

            // Crea un oggetto Photo e imposta i suoi valori
            Photo photo = new Photo();
            photo.setTitolo("Alba ");
            photo.setDescrizione("Una bellissima alba vista dalla montagna");
            photo.setVisible(true);
            photo.setCreatedAt(LocalDateTime.now());
            photo.setCover(imageBytes);
            photo.setUser(user);
            photo.setCategories(Collections.emptyList()); // Imposta categorie vuote o reali se necessario

            // Salva la foto nel database
            photoRepository.save(photo);

            // Ripeti per altre immagini...
        }
    }
}
