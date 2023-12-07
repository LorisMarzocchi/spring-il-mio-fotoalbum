package com.experis.springilmiofotoalbum.repository;

import com.experis.springilmiofotoalbum.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByOrderByName();

    @Query("SELECT c FROM Category c WHERE c.name IN :names")
    List<Category> findAllByName(List<String> names);

    boolean existsByName(String name);

}
