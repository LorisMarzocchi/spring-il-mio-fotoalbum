package com.experis.springilmiofotoalbum.service;

import com.experis.springilmiofotoalbum.exception.PhotoNotFoundException;
import com.experis.springilmiofotoalbum.model.Photo;
import com.experis.springilmiofotoalbum.model.User;
import com.experis.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {
    @Autowired
    private PhotoRepository photoRepository;

//    public List<Photo> getPhotoSearch(Optional<String> search) {
//        if (search.isPresent()) {
//            return photoRepository.findByTitoloContainingIgnoreCaseOrDescrizioneContaining(search.get(), search.get());
//        } else {
//            return photoRepository.findAll();
//        }
//    }

    public List<Photo> getVisibleFotoList(Optional<String> search) {
        if (search.isPresent()) {

            return photoRepository.findByTitoloContainingIgnoreCaseOrDescrizioneContainingAndVisible(search.get(), search.get(), true);
        } else {
            return photoRepository.findAllVisiblePhotos();
        }
    }

    public Photo getPhotoById(Integer id) throws PhotoNotFoundException {
        Optional<Photo> result = photoRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PhotoNotFoundException("Photo with id " + id + " not found");
        }
    }

    // Metodo per ottenere le foto di un utente specifico
    public List<Photo> getPhotosByUser(User user) {
        return photoRepository.findByUser(user);
    }

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    //  metodo savePhoto per associare la foto all'utente
    public Photo savePhoto(Photo photo, User user) {
        photo.setUser(user); //
        return savePhoto(photo); // Riutilizza il metodo savePhoto esistente
    }

    public Photo savePhoto(Photo photo) {
        photo.setId(null);
        try {
            return photoRepository.save(photo);
        } catch (RuntimeException e) {
            throw new RuntimeException("Errore durante il salvataggio della foto: " + e.getMessage(), e);
        }
    }

    public Photo updatePhoto(Photo photo) {
        Photo photoToEdit = getPhotoById(photo.getId());
        photoToEdit.setTitolo(photo.getTitolo());
        photoToEdit.setDescrizione(photo.getDescrizione());
        photoToEdit.setUrlImage(photo.getUrlImage());
        photoToEdit.setVisible(photo.isVisible());
        photoToEdit.setCategories(photo.getCategories());
        return photoRepository.save(photoToEdit);
    }

    public void deletePhoto(Integer id) {
        photoRepository.deleteById(id);
    }


}
