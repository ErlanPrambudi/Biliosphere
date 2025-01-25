package com.example.biliosphere.service;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/25/2025 4:00 PM
@Last Modified 1/25/2025 4:00 PM
Version 1.0
*/

import com.example.biliosphere.exception.BadRequestException;
import com.example.biliosphere.exception.ResourceNotFoundException;
import com.example.biliosphere.model.Genders;
import com.example.biliosphere.repo.GenderRepository;
import com.example.biliosphere.repo.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {

    @Autowired
    private GenderRepository genderRepository;

    // Menampilkan semua gender
    public List<Genders> findAll() {
        return genderRepository.findAll();
    }

    // Menampilkan gender berdasarkan ID
    public Genders findById(String id) {
        return genderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gender with ID " + id + " not found"));
    }

    // Menyimpan gender baru
    public Genders save(Genders gender) {
        // Validasi gender name
        if (gender.getGender() == null || gender.getGender().isEmpty()) {
            throw new BadRequestException("Gender name is required");
        }

        // Memastikan gender name unik
        if (genderRepository.existsByGender(gender.getGender())) {
            throw new BadRequestException("Gender name " + gender.getGender() + " already exists");
        }

        return genderRepository.save(gender);
    }

    // Mengupdate gender berdasarkan ID
    public Genders update(String id, Genders updatedGender) {
        Genders existingGender = genderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gender with ID " + id + " not found"));

        // Update field gender
        existingGender.setGender(updatedGender.getGender());

        return genderRepository.save(existingGender);
    }

    // Menghapus gender berdasarkan ID
    public void delete(String id) {
        if (!genderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Gender with ID " + id + " not found");
        }
        genderRepository.deleteById(id);
    }
}