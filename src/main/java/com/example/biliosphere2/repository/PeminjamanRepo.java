package com.example.biliosphere2.repository;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 5:47 PM
@Last Modified 2/4/2025 5:47 PM
Version 1.0
*/


import com.example.biliosphere2.model.Peminjaman;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PeminjamanRepo extends JpaRepository<Peminjaman, Long> {

    // Menemukan peminjaman berdasarkan nama pengguna dengan paging
    public Page<Peminjaman> findByUserNamaContainsIgnoreCase(Pageable pageable, String nama);

    // Menemukan peminjaman berdasarkan judul buku dengan paging
    public Page<Peminjaman> findByBukuJudulContainsIgnoreCase(Pageable pageable, String nama);

    // Menemukan peminjaman berdasarkan status pengembalian dengan paging
    public Page<Peminjaman> findByStatusPengembalian(Pageable pageable, Boolean statusPengembalian);

    // Menemukan peminjaman berdasarkan ID user dan status pengembalian
    public Optional<Peminjaman> findByUserIdAndStatusPengembalian(Long userId, Boolean statusPengembalian);

    // Menemukan peminjaman berdasarkan rentang tanggal pinjam dan tanggal kembali
    public Page<Peminjaman> findByTanggalPinjamBetweenAndTanggalKembaliBetween(
            Pageable pageable, Date tanggalPinjamStart, Date tanggalPinjamEnd, Date tanggalKembaliStart, Date tanggalKembaliEnd
    );

    // Menemukan peminjaman berdasarkan ID peminjaman
    public Optional<Peminjaman> findById(Long id);
}
