package com.example.biliosphere2.repository;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 4:39 PM
@Last Modified 2/4/2025 4:39 PM
Version 1.0
*/

import com.example.biliosphere2.model.Denda;
import com.example.biliosphere2.model.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DendaRepo extends JpaRepository<Denda, Long> {

    // ✅ Cari denda berdasarkan peminjaman
    Optional<Denda> findByPeminjaman(Peminjaman peminjaman);
}

