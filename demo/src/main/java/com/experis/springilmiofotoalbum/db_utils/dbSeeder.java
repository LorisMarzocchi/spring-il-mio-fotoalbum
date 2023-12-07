package com.experis.springilmiofotoalbum.db_utils;

import com.experis.springilmiofotoalbum.model.Category;
import com.experis.springilmiofotoalbum.model.Photo;
import com.experis.springilmiofotoalbum.model.User;
import com.experis.springilmiofotoalbum.repository.CategoryRepository;
import com.experis.springilmiofotoalbum.repository.PhotoRepository;
import com.experis.springilmiofotoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class dbSeeder implements CommandLineRunner {
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count() == 0) {
            String[] categories = {"Natura", "Città", "Persone", "Ritratti", "Arte", "Animali", "Tecnologia", "Mare", "Paesaggi", "Interni", "Alba"};
            for (String nameCategory : categories) {
                Category category = new Category();
                category.setName(nameCategory);
                categoryRepository.save(category);
            }
        }
        if (photoRepository.count() == 0) {
            // Crea un utente
            User userJohn = userRepository.findById(1).orElse(null);
            User userJane = userRepository.findById(2).orElse(null);
            User userDavid = userRepository.findById(3).orElse(null);
            User userNicole = userRepository.findById(4).orElse(null);

            // Foto 1 Jhon (SUPER_ADMIN)

            // Recupero le categorie da assegnare
            List<Category> categoryListPhoto1 = categoryRepository.findAllByName(Arrays.asList("Città", "Persone", "Alba"));
            // Esempio come leggere un file immagine
            ClassPathResource imgFile1 = new ClassPathResource("static/images/pexels-alex-umbelino-18812095.jpg");
            byte[] imageBytes1 = Files.readAllBytes(imgFile1.getFile().toPath());

            // Crea un oggetto Photo e imposta i suoi valori
            Photo photo1 = new Photo();
            photo1.setTitolo("Passi Urbani  ");
            photo1.setDescrizione("Un momento tranquillo in cui la città si risveglia");
            photo1.setVisible(true);
            photo1.setCreatedAt(LocalDateTime.now());
            photo1.setCover(imageBytes1);
            photo1.setUser(userJohn);
            photo1.setCategories(categoryListPhoto1); // Imposta categorie vuote o reali se necessario
            // Salva la foto nel database
            photoRepository.save(photo1);

            //////////////////////////////////////////////////////////////////////////////////

            // Foto 2 Jhon

            ClassPathResource imgFile2 = new ClassPathResource("static/images/image_1.png");
            byte[] imageBytes2 = Files.readAllBytes(imgFile2.getFile().toPath());

            List<Category> categoryListPhoto2 = categoryRepository.findAllByName(Arrays.asList("Ritratti", "Tecnologia", "Arte"));

            Photo photo2 = new Photo();
            photo2.setTitolo("Tecnologia e Arte");
            photo2.setDescrizione("Quando l'arte digitale incontra l'innovazione e si fonde con il comfort domestico");
            photo2.setVisible(true);
            photo2.setCreatedAt(LocalDateTime.now());
            photo2.setCover(imageBytes2);
            photo2.setUser(userJohn);
            photo2.setCategories(categoryListPhoto2); // Imposta categorie vuote o reali se necessario
            // Salva la foto nel database
            photoRepository.save(photo2);

            // Foto 3 Jhon

            ClassPathResource imgFile3 = new ClassPathResource("static/images/pexels-artūras-kokorevas-19082544.jpg");
            byte[] imageBytes3 = Files.readAllBytes(imgFile3.getFile().toPath());

            List<Category> categoryListPhoto3 = categoryRepository.findAllByName(Arrays.asList("Ritratti", "Mare", "Arte"));

            Photo photo3 = new Photo();
            photo3.setTitolo("Marina al Mattino ");
            photo3.setDescrizione("Una vista pacifica delle barche che ondeggiano con il ritmo del mare.");
            photo3.setVisible(true);
            photo3.setCreatedAt(LocalDateTime.now());
            photo3.setCover(imageBytes3);
            photo3.setUser(userJohn);
            photo3.setCategories(categoryListPhoto3); // Imposta categorie vuote o reali se necessario
            // Salva la foto nel database
            photoRepository.save(photo3);

            // Foto 4 Jhon

            ClassPathResource imgFile4 = new ClassPathResource("static/images/pexels-reinaldo-simoes-17990324.jpg");
            byte[] imageBytes4 = Files.readAllBytes(imgFile4.getFile().toPath());

            List<Category> categoryListPhoto4 = categoryRepository.findAllByName(Arrays.asList("Natura", "Mare", "Arte"));

            Photo photo4 = new Photo();
            photo4.setTitolo("Costa Rocciosa ");
            photo4.setDescrizione("Vista su una costa rocciosa battuta dalle onde del mare");
            photo4.setVisible(true);
            photo4.setCreatedAt(LocalDateTime.now());
            photo4.setCover(imageBytes4);
            photo4.setUser(userJohn);
            photo4.setCategories(categoryListPhoto4); // Imposta categorie vuote o reali se necessario
            // Salva la foto nel database
            photoRepository.save(photo4);

            // Foto 5 Jane

            ClassPathResource imgFile5 = new ClassPathResource("static/images/pexels-tony-pham-4869369.jpg");
            byte[] imageBytes5 = Files.readAllBytes(imgFile5.getFile().toPath());

            List<Category> categoryListPhoto5 = categoryRepository.findAllByName(Arrays.asList("Ritratti", "Città", "Arte", "Persone"));

            Photo photo5 = new Photo();
            photo5.setTitolo("Orizzonti Urbani ");
            photo5.setDescrizione("L'architettura moderna si staglia contro il cielo azzurro, riflettendosi nelle acque tranquille del porto");
            photo5.setVisible(true);
            photo5.setCreatedAt(LocalDateTime.now());
            photo5.setCover(imageBytes5);
            photo5.setUser(userJane);
            photo5.setCategories(categoryListPhoto5); // Imposta categorie vuote o reali se necessario
            // Salva la foto nel database
            photoRepository.save(photo5);
        }
    }
}
