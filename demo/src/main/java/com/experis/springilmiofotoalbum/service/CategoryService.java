package com.experis.springilmiofotoalbum.service;

import com.experis.springilmiofotoalbum.exception.CategoryNameUniqueException;
import com.experis.springilmiofotoalbum.exception.CategoryNotFoundException;
import com.experis.springilmiofotoalbum.model.Category;
import com.experis.springilmiofotoalbum.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findByOrderByName();
    }

    public Category save(Category category) throws CategoryNameUniqueException {
        if (categoryRepository.existsByName(category.getName())) {
            throw new CategoryNameUniqueException(category.getName());
        }
        category.setName(category.getName().toLowerCase());
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Integer id) throws CategoryNotFoundException {
        Optional<Category> result = categoryRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new CategoryNotFoundException("Category with id " + id + " not found");
        }
    }

    public void deleteCategory(Integer id) {
        categoryRepository.deleteById(id);
    }
}
