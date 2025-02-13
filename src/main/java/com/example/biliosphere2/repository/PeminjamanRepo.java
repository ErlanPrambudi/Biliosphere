package com.example.biliosphere2.repository;

import com.example.biliosphere2.model.Peminjaman;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeminjamanRepo extends JpaRepository<Peminjaman, Long> {

    boolean existsByUser_IdAndBuku_IdAndStatusPengembalian_IdNot(Long userId, Long bukuId, Long statusPengembalianId);

    // Cari peminjaman berdasarkan ID User
    Page<Peminjaman> findByUser_Id(Pageable pageable, Long userId);

    // Cari peminjaman berdasarkan ID Buku
    Page<Peminjaman> findByBuku_Id(Pageable pageable, Long bukuId);

    // Cari peminjaman berdasarkan ID Status Pengembalian
    Page<Peminjaman> findByStatusPengembalian_Id(Pageable pageable, Long statusPengembalianId);

//     Cek apakah user sudah meminjam buku ini sebelumnya
    boolean existsByUser_IdAndBuku_Id(Long userId, Long bukuId);

    // Cari semua peminjaman yang belum dikembalikan (tanggalKembali masih null)
    List<Peminjaman> findByTanggalKembaliIsNull();
    //Menghitung jumlah peminjaman yang masih aktif (belum dikembalikan).
    long countByUserIdAndTanggalKembaliIsNull(Long userId);
}