package com.example.biliosphere.controller;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/25/2025 4:05 PM
@Last Modified 1/25/2025 4:05 PM
Version 1.0
*/

import com.example.biliosphere.model.Genders;
import com.example.biliosphere.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/genders")
public class GenderController {

    @Autowired
    private GenderService genderService;

    // Endpoint untuk menampilkan semua gender
    @GetMapping
    public ResponseEntity<List<Genders>> getAllGenders() {
        List<Genders> genders = genderService.findAll();
        return ResponseEntity.ok(genders);
    }

    // Endpoint untuk menampilkan gender berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Genders> getGenderById(@PathVariable String id) {
        Genders gender = genderService.findById(id);
        return ResponseEntity.ok(gender);
    }

    // Endpoint untuk menyimpan gender baru
    @PostMapping
    public ResponseEntity<Genders> createGender(@RequestBody Genders gender) {
        Genders createdGender = genderService.save(gender);
        return ResponseEntity.ok(createdGender);
    }

    // Endpoint untuk mengupdate gender berdasarkan ID
    @PutMapping("/{id}")
    public ResponseEntity<Genders> updateGender(@PathVariable String id, @RequestBody Genders gender) {
        Genders updatedGender = genderService.update(id, gender);
        return ResponseEntity.ok(updatedGender);
    }

    // Endpoint untuk menghapus gender berdasarkan ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGender(@PathVariable String id) {
        genderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}