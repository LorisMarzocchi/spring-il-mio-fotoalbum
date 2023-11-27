package com.experis.springilmiofotoalbum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 3, max = 100, message = "il nome della categoria non può essere inferiore a 3 o maggiore di 100 caratteri")
    @Column(nullable = false, unique = true)
    @NotBlank(message = "il nome della categoria non puo essere vuoto")
    private String name;
    @ManyToMany(mappedBy = "categories")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Photo> photos = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
