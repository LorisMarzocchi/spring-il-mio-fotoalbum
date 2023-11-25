package com.experis.springilmiofotoalbum.repository;

import com.experis.springilmiofotoalbum.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
