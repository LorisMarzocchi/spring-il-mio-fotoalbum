package com.experis.springilmiofotoalbum.repository;

import com.experis.springilmiofotoalbum.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    List<Photo> findByTitoloContainingIgnoreCase(String search);
}
