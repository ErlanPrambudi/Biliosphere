//package com.example.biliosphere2.repository;
//
///*
//IntelliJ IDEA 2024.3 (Ultimate Edition)
//Build #IU-243.21565.193, built on November 13, 2024
//@Author Dell Erlan Prambudi
//Java Developer
//Created on 2/4/2025 4:39 PM
//@Last Modified 2/4/2025 4:39 PM
//Version 1.0
//*/
//
//import com.example.biliosphere2.model.Denda;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.List;
//
//public interface DendaRepo extends JpaRepository<Denda, Long> {
//
//    // Mencari denda berdasarkan ID peminjaman dengan paginasi
//   public Page<Denda> findByPeminjamanId(Long peminjamanId, Pageable pageable);
//
//    // Mencari denda berdasarkan status pembayaran dengan paginasi
//   public Page<Denda> findByStatusPembayaran(Boolean statusPembayaran, Pageable pageable);
//
//    // (Opsional) Mencari semua denda berdasarkan ID peminjaman tanpa paginasi
//    List<Denda> findByPeminjamanId(Long peminjamanId);
//}
