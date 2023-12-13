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


    // Metodo per ottenere una lista di foto visibili, con opzionale filtro di ricerca

    public List<Photo> getVisibleFotoList(Optional<String> search) {
        if (search.isPresent()) {
            // Se c'è un criterio di ricerca, restituisce le foto che corrispondono alla ricerca e sono visibili
            return photoRepository.findByTitoloContainingIgnoreCaseOrDescrizioneContainingAndVisible(search.get(), search.get(), true);
        } else {
            // Se non c'è un criterio di ricerca, restituisce tutte le foto visibili
            return photoRepository.findAllVisiblePhotos();
        }
    }

    // Metodo per ottenere una foto per ID
    public Photo getPhotoById(Integer id) throws PhotoNotFoundException {
        Optional<Photo> result = photoRepository.findById(id);
        if (result.isPresent()) {
            // Se la foto è presente, la restituisce, altrimenti lancia un'eccezione
            return result.get();
        } else {
            throw new PhotoNotFoundException("Photo with id " + id + " not found");
        }
    }

    // Metodo per ottenere le foto di un utente specifico, con opzionale filtro di ricerca
    public List<Photo> getPhotosByUser(User user, Optional<String> search) {
        if (search.isPresent()) {
            // Restituisce le foto dell'utente che corrispondono alla ricerca
            return photoRepository.findByUserAndTitoloContainingIgnoreCaseOrDescrizioneContainingIgnoreCase(user, search.get(), search.get());
        } else {
            // Restituisce tutte le foto dell'utente
            return photoRepository.findByUser(user);
        }
    }

    // Metodo per ottenere tutte le foto, con opzionale filtro di ricerca
    public List<Photo> getAllPhotos(Optional<String> search) {
        if (search.isPresent()) {
            // Restituisce tutte le foto che corrispondono alla ricerca
            return photoRepository.findByTitoloContainingIgnoreCaseOrDescrizioneContaining(search.get(), search.get());
        } else {
            // Restituisce tutte le foto
            return photoRepository.findAll();
        }
    }


    //  metodo savePhoto(CREATE)
    // Metodo per salvare una foto nel database, associandola a un utente
    public Photo savePhoto(Photo photo, User user) {
        if (user != null) {
            // Associa l'utente alla foto, se l'utente è fornito
            photo.setUser(user);
        }
        // Imposta l'ID su null per indicare che si tratta di una nuova foto
        photo.setId(null);

        try {
            // Salva la foto nel repository
            return photoRepository.save(photo);
        } catch (RuntimeException e) {
            throw new RuntimeException("Errore durante il salvataggio della foto: " + e.getMessage(), e);
        }
    }

    //  metodo savePhoto(CREATE)
    // Metodo per salvare una nuova foto, riceve un PhotoDto e un User

    /**
     * Salva una foto nel database dopo averla associata a un utente.
     * Questo metodo viene chiamato quando si crea una nuova foto.
     * Utilizza un PhotoDto, che è un oggetto semplificato ricevuto dalla view,
     * e lo converte in un'entità Photo prima di salvarlo.
     *
     * @param photoDto DTO della foto da salvare.
     * @param user     Utente a cui associare la foto.
     * @return Foto salvata.
     * @throws IOException Se c'è un problema nella conversione dei dati.
     */
    public Photo savePhoto(PhotoDto photoDto, User user) throws PhotoNotFoundException, IOException {
        // Converte il PhotoDto in un oggetto Photo
        // Convertendo il DTO in un'entità Photo, si adatta il modello di trasferimento dati
        // alla struttura richiesta dal database.
        Photo photo = convertDtoToPhoto(photoDto);
        // Salva la foto nel database dopo la conversione, associandola all'utente
        return savePhoto(photo, user);
    }

    // Metodo per convertire un PhotoDto in un oggetto Photo

    /**
     * Converte un PhotoDto in un oggetto Photo.
     * Questa conversione è necessaria perché il DTO è un formato ottimizzato per il trasferimento di dati,
     * mentre l'entità Photo rappresenta il modello di dati come definito per il database.
     *
     * @param photoDto DTO da convertire.
     * @return Oggetto Photo convertito.
     * @throws IOException Se c'è un problema nella conversione dei dati.
     */
    private static Photo convertDtoToPhoto(PhotoDto photoDto) throws IOException {
        // Effettiva conversione dei dati dal formato DTO al formato entità //
        Photo photo = new Photo();
        // Assegna i valori dal PhotoDto all'oggetto Photo
        photo.setId(photoDto.getId());
        photo.setTitolo(photoDto.getTitolo());
        photo.setDescrizione(photoDto.getDescrizione());
        photo.setVisible(photoDto.isVisible());
        photo.setCategories(photoDto.getCategories());
        // Gestisce il file di cover, se presente
        if (photoDto.getCoverFile() != null && !photoDto.getCoverFile().isEmpty()) {
            byte[] bytes = photoDto.getCoverFile().getBytes();
            photo.setCover(bytes);
        }
        return photo;
    }

    // Metodo per convertire un oggetto Photo in un PhotoDto

    private static PhotoDto convertPhotoToDto(Photo photo) throws IOException {
        PhotoDto photoDto = new PhotoDto();
        // Assegna i valori dal Photo all'oggetto PhotoDto
        photoDto.setId(photo.getId());
        photoDto.setTitolo(photo.getTitolo());
        photoDto.setCategories(photo.getCategories());
        photoDto.setVisible(photo.isVisible());
        photoDto.setCategories(photo.getCategories());
        return photoDto;
    }

    // Metodo per ottenere un PhotoDto per un ID specifico
    public PhotoDto getPhotoDtoById(Integer id) throws IOException {
        // Recupera la foto e la converte in PhotoDto
        Photo photo = getPhotoById(id);
        return convertPhotoToDto(photo);

    }

    // Metodo per aggiornare i dettagli di una foto esistente
    public Photo updatePhoto(Photo photo) {
        Photo photoToEdit = getPhotoById(photo.getId());
        // Aggiorna i dettagli della foto con i nuovi valori
        photoToEdit.setTitolo(photo.getTitolo());
        photoToEdit.setDescrizione(photo.getDescrizione());
        photoToEdit.setVisible(photo.isVisible());
        photoToEdit.setCategories(photo.getCategories());
        if (photo.getCover() != null && photo.getCover().length > 0) {
            photoToEdit.setCover(photo.getCover());
        }
        return photoRepository.save(photoToEdit);
    }

    // Metodo per aggiornare una foto utilizzando un PhotoDto

    /**
     * Aggiorna i dettagli di una foto esistente utilizzando un DTO.
     * Questo metodo viene utilizzato per aggiornare le informazioni di una foto esistente,
     * trasformando i dati forniti in formato DTO in un'entità Photo per l'aggiornamento nel database.
     *
     * @param photoDto DTO con i dettagli aggiornati.
     * @return Foto aggiornata.
     * @throws IOException Se c'è un problema nella conversione dei dati.
     */
    public Photo updatePhoto(PhotoDto photoDto) throws IOException {
        // Analogamente al salvataggio, la conversione è essenziale per trasformare i dati
        // ricevuti dal frontend in un formato utilizzabile dal livello di persistenza.
        Photo photo = convertDtoToPhoto(photoDto);
        return updatePhoto(photo);
    }

    // Metodo per eliminare una foto dal database
    public void deletePhoto(Integer id) {
        // Elimina la foto con l'ID specificato
        photoRepository.deleteById(id);
    }

}
