//package com.example.biliosphere2.repository;
//
///*
//IntelliJ IDEA 2024.3 (Ultimate Edition)
//Build #IU-243.21565.193, built on November 13, 2024
//@Author Dell Erlan Prambudi
//Java Developer
//Created on 2/5/2025 3:06 AM
//@Last Modified 2/5/2025 3:06 AM
//Version 1.0
//*/
//
//import com.example.biliosphere2.model.StatusPengembalian;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface StatusPengembalianRepo extends JpaRepository<StatusPengembalian, Long> {
//
//    Page<StatusPengembalian> findByStatusContainsIgnoreCase(Pageable pageable, String status);
//    Optional<StatusPengembalian> findById(Long id);// Menemukan status pengembalian berdasarkan ID status
//    Optional<StatusPengembalian> findByStatus(String status);// Menemukan status pengembalian berdasarkan status tertentu
//}