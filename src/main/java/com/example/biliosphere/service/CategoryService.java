package com.example.biliosphere.service;

import com.example.biliosphere.exception.ResourceNotFoundException;
import com.example.biliosphere.model.Categories;
import com.example.biliosphere.model.Users;
import com.example.biliosphere.repo.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/20/2025 3:31 PM
@Last Modified 1/20/2025 3:31 PM
Version 1.0
*/
@Service

public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Categories findById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("category with id "+id+" not found"));
    }
    public List<Categories> findAll() {
        return categoryRepository.findAll();
    }
    public Categories save(Categories categories) {
        categories.setId(UUID.randomUUID().toString());
        return categoryRepository.save(categories);
    }
    @Transactional
    public void update(String id, Categories category) {
        // Periksa apakah ID ada di database
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }

        // Ambil data category yang ada
        Categories existingCategory = categoryRepository.findById(id).get();

        // Hanya perbarui nama category
        existingCategory.setNamaCategory(category.getNamaCategory());

        // Simpan perubahan ke database
        categoryRepository.save(existingCategory);
    }
    @Transactional
    public void delete(String id) {
        // Periksa apakah ID ada di database
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category with id " + id + " not found");
        }

        // Hapus kategori berdasarkan ID
        categoryRepository.deleteById(id);
    }
}
