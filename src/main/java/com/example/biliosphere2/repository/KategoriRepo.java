package com.example.biliosphere2.repository;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 1:07 AM
@Last Modified 2/4/2025 1:07 AM
Version 1.0
*/

import com.example.biliosphere2.model.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface KategoriRepo extends JpaRepository<Kategori,Long> {
    Page<Kategori> findByNamaKategoriContainsIgnoreCase(Pageable pageable, String nama);
}
