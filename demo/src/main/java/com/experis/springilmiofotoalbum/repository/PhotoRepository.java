package com.experis.springilmiofotoalbum.repository;

import com.experis.springilmiofotoalbum.model.Photo;
import com.experis.springilmiofotoalbum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    //    List<Photo> findByTitoloContainingIgnoreCase(String search);
    List<Photo> findByTitoloContainingIgnoreCaseOrDescrizioneContaining(String search, String descriptionKeyword);

    @Query("SELECT p FROM Photo p WHERE (p.titolo LIKE %?1% OR p.descrizione LIKE %?2%) AND p.visible = true")
    List<Photo> findByTitoloContainingIgnoreCaseOrDescrizioneContainingAndVisible(String titleKeyword, String descriptionKeyword, boolean visible);
    /// FUNZIONA ANCHE SE LA CHIAMI "asdfasdf" PRENDE IL VALORE DELLA QUERY

    //
//    List<Photo> findByVisible(boolean visible);
    @Query("SELECT p FROM Photo p WHERE p.visible = true")
    List<Photo> findAllVisiblePhotos();

    List<Photo> findByUser(User user);

}
