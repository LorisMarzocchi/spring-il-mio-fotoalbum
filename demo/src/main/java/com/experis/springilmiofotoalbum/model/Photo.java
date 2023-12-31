package com.experis.springilmiofotoalbum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "il nome non puo essere vuoto")
//    @Column(nullable = false)
    @Size(max = 100, message = "il campo non può essere minore di 5 e maggiore di 100 caratteri")
    @Column(length = 100, nullable = false, unique = true)
    private String titolo;
    @NotBlank(message = "il campo descrizione non puo essere vuoto")
    @Column(nullable = false)
    @Size(max = 255, message = "il campo non può essere minore di 5 maggiore di 255 caratteri")
    private String descrizione;
    @Lob
    @Column(length = 16777215)
    @JsonIgnore
    private byte[] cover;

    @NotNull
    private boolean visible = true;
    @CreationTimestamp
    private LocalDateTime createdAt;
    //    @JoinTable(
//            name = "photos_categories",
//            joinColumns = @JoinColumn(name = "photos_id"),
//            inverseJoinColumns = @JoinColumn(name = "categories_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> categories;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }


    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
