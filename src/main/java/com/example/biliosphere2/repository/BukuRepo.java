package com.example.biliosphere2.repository;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 5:02 AM
@Last Modified 2/4/2025 5:02 AM
Version 1.0
*/


import com.example.biliosphere2.model.Buku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BukuRepo extends JpaRepository<Buku,Long> {
   public Page<Buku> findByJudulContainsIgnoreCase(Pageable pageable, String nama);
   public Page<Buku> findByPenulisContainsIgnoreCase(Pageable pageable, String nama);
   public Page<Buku> findByPenerbitContainsIgnoreCase(Pageable pageable, String nama);
   public Page<Buku> findByTahunTerbit(Pageable pageable, Integer tahunTerbit);

   Optional<Buku> findByJudul(String judul);
}
