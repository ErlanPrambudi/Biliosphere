package com.example.biliosphere2.repository;

import com.example.biliosphere2.model.Peminjaman;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeminjamanRepo extends JpaRepository<Peminjaman, Long> {

    // Pencarian berdasarkan relasi
    Page<Peminjaman> findByUser_Id(Pageable pageable, Long userId);
    Page<Peminjaman> findByBuku_Id(Pageable pageable, Long bukuId);
    Page<Peminjaman> findByStatusPengembalian_Id(Pageable pageable, Long statusPengembalianId);
    // Tambahkan metode pencarian berdasarkan userId dan bukuId
    Page<Peminjaman> findByUser_IdAndBuku_Id(Pageable pageable, Long userId, Long bukuId);
}