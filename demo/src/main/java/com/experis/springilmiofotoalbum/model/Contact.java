package com.experis.springilmiofotoalbum.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "il nome non può essere vuoto, inferiore a 3 o maggiore di 100 caratteri")
    @Size(min = 3, max = 100)
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank(message = "il campo descrizione non puo essere vuoto")
//    @Column(nullable = false)
//    @Size(max = 255, message = "il campo non può essere minore di 5 maggiore di 255 caratteri")
 
    private String message;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}