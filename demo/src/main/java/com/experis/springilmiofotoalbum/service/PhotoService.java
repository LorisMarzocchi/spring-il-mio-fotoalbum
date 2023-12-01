package com.experis.springilmiofotoalbum.service;

import com.experis.springilmiofotoalbum.dto.PhotoDto;
import com.experis.springilmiofotoalbum.exception.PhotoNotFoundException;
import com.experis.springilmiofotoalbum.model.Photo;
import com.experis.springilmiofotoalbum.model.User;
import com.experis.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public List<Photo> getPhotosByUser(User user, Optional<String> search) {
        if (search.isPresent()) {
            return photoRepository.findByUserAndTitoloContainingIgnoreCaseOrDescrizioneContainingIgnoreCase(user, search.get(), search.get());
        } else {
            return photoRepository.findByUser(user);
        }
    }

    public List<Photo> getAllPhotos(Optional<String> search) {
        if (search.isPresent()) {

            return photoRepository.findByTitoloContainingIgnoreCaseOrDescrizioneContaining(search.get(), search.get());
        } else {
            return photoRepository.findAll();
        }
    }

    // Con overload di metodi separa meglio i compiti
    //  metodo savePhoto per associare la foto all'utente
//    public Photo savePhoto(Photo photo, User user) {
//        photo.setUser(user); //
//        return savePhoto(photo); // Riutilizza il metodo savePhoto esistente
//    }
//
//    public Photo savePhoto(Photo photo) {
//        photo.setId(null);
//        try {
//            return photoRepository.save(photo);
//        } catch (RuntimeException e) {
//            throw new RuntimeException("Errore durante il salvataggio della foto: " + e.getMessage(), e);
//        }
//    }
    public Photo savePhoto(Photo photo, User user) {
        if (user != null) {
            photo.setUser(user); // Associa l'utente alla foto, se l'utente è fornito
        }

        photo.setId(null); // Imposta l'ID della foto su null

        try {
            return photoRepository.save(photo); // Salva la foto nel repository
        } catch (RuntimeException e) {
            throw new RuntimeException("Errore durante il salvataggio della foto: " + e.getMessage(), e);
        }
    }

//    public Photo savePhoto(PhotoDto photoDto, User user) {
//        try {
//            Photo photo = convertDtoToPhoto(photoDto);
//            return photoRepository.save(photo); // Salva la foto nel repository
//        } catch (RuntimeException e) {
//            throw new RuntimeException("Errore durante il salvataggio della foto: " + e.getMessage(), e);
//        } catch (IOException e) {
//            throw new RuntimeException("Errore durante il salvataggio della fotoDto: " + e.getMessage(), e);
//        }
//    }


    //    public Photo savePhoto(PhotoDto photoDto) throws PhotoNotFoundException {
//        try {
//            Photo photo = convertDtoToPhoto(photoDto);
//            return savePhoto(photo);
//
//        } catch (IOException e) {
//            throw new PhotoNotFoundException(e.getMessage());
//
//        }
//    }
    public Photo savePhoto(PhotoDto photoDto, User user) throws PhotoNotFoundException, IOException {
        Photo photo = convertDtoToPhoto(photoDto);

        return savePhoto(photo, user);
    }


    private static Photo convertDtoToPhoto(PhotoDto photoDto) throws IOException {
        Photo photo = new Photo();
        photo.setId(photoDto.getId());
        photo.setTitolo(photoDto.getTitolo());
        photo.setDescrizione(photoDto.getDescrizione());
        photo.setVisible(photoDto.isVisible());
        photo.setCategories(photoDto.getCategories());
        if (photoDto.getCoverFile() != null && !photoDto.getCoverFile().isEmpty()) {
            byte[] bytes = photoDto.getCoverFile().getBytes();
            photo.setCover(bytes);
        }
        return photo;
    }

    private static PhotoDto convertPhotoToDto(Photo photo) throws IOException {
        PhotoDto photoDto = new PhotoDto();
        photoDto.setId(photo.getId());
        photoDto.setTitolo(photo.getTitolo());
        photoDto.setCategories(photo.getCategories());
        photoDto.setVisible(photo.isVisible());
        photoDto.setCategories(photo.getCategories());
        return photoDto;
    }

    public PhotoDto getPhotoDtoById(Integer id) throws IOException {
        Photo photo = getPhotoById(id);
        return convertPhotoToDto(photo);

    }

    public Photo updatePhoto(Photo photo) {
        Photo photoToEdit = getPhotoById(photo.getId());
        photoToEdit.setTitolo(photo.getTitolo());
        photoToEdit.setDescrizione(photo.getDescrizione());
        photoToEdit.setVisible(photo.isVisible());
        photoToEdit.setCategories(photo.getCategories());
        if (photo.getCover() != null && photo.getCover().length > 0) {
            photoToEdit.setCover(photo.getCover());
        }
        return photoRepository.save(photoToEdit);
    }

    public Photo updatePhoto(PhotoDto photoDto) throws IOException {
        Photo photo = convertDtoToPhoto(photoDto);
        return updatePhoto(photo);
    }

    public void deletePhoto(Integer id) {
        photoRepository.deleteById(id);
    }


}
