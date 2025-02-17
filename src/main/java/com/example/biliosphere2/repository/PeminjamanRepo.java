package com.example.biliosphere2.repository;

import com.example.biliosphere2.model.Peminjaman;
import com.example.biliosphere2.model.enums.StatusPengembalianEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeminjamanRepo extends JpaRepository<Peminjaman, Long> {

    // ✅ Hapus method yang pakai ID status pengembalian karena kita pakai Enum
    boolean existsByUser_IdAndBuku_IdAndStatusPengembalianNot(Long userId, Long bukuId, StatusPengembalianEnum statusPengembalian);

    // ✅ Cari peminjaman berdasarkan ID User
    Page<Peminjaman> findByUser_Id(Pageable pageable, Long userId);

    // ✅ Cari peminjaman berdasarkan ID Buku
    Page<Peminjaman> findByBuku_Id(Pageable pageable, Long bukuId);

    // ✅ Cari peminjaman berdasarkan Status Pengembalian (Pakai Enum langsung)
    Page<Peminjaman> findByStatusPengembalian(Pageable pageable, StatusPengembalianEnum statusPengembalian);

    // ✅ Cek apakah user sudah meminjam buku ini sebelumnya
    boolean existsByUser_IdAndBuku_Id(Long userId, Long bukuId);

    boolean existsByUser_IdAndBuku_IdAndStatusPengembalianNotInAndTanggalKembaliIsNull(
            Long userId,
            Long bukuId,
            List<StatusPengembalianEnum> statusList
    );
    // ✅ Cari semua peminjaman yang belum dikembalikan (tanggalKembali masih null)
    List<Peminjaman> findByTanggalKembaliIsNull();

    // ✅ Menghitung jumlah peminjaman yang masih aktif (belum dikembalikan)
    long countByUser_IdAndTanggalKembaliIsNull(Long userId);
}
