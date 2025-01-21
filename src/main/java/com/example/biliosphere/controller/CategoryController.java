package com.example.biliosphere.controller;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/20/2025 4:37 PM
@Last Modified 1/20/2025 4:37 PM
Version 1.0
*/

import com.example.biliosphere.model.Categories;
import com.example.biliosphere.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // GET: Ambil semua kategori
    @GetMapping("/categories")
    public List<Categories> findAll() {
        return categoryService.findAll();
    }

    // GET: Ambil kategori berdasarkan ID
    @GetMapping("/categories/{id}")
    public Categories findById(@PathVariable("id") String id) {
        return categoryService.findById(id);
    }

    // POST: Simpan kategori baru
    @PostMapping("/categories")
    public Categories save(@RequestBody Categories categories) {
        // Validasi jika ID dikirimkan dalam request body
        if (categories.getId() != null) {
            throw new IllegalArgumentException("ID should not be provided for creating a new category");
        }
        return categoryService.save(categories);
    }

    // PUT: Update kategori berdasarkan ID
    @PutMapping("/categories/{id}")
    public void update(@PathVariable("id") String id, @RequestBody Categories categories) {
        categoryService.update(id, categories);
    }

    // DELETE: Hapus kategori berdasarkan ID
    @DeleteMapping("/categories/{id}")
    public void delete(@PathVariable("id") String id) {
        categoryService.delete(id);
    }
}
