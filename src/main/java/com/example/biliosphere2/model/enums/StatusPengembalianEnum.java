package com.example.biliosphere2.model.enums;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/14/2025 11:48 PM
@Last Modified 2/14/2025 11:48 PM
Version 1.0
*/

public enum StatusPengembalianEnum {
    DIPINJAM,                    // Status awal saat buku masih dipinjam
    DIKEMBALIKAN,               // Buku dikembalikan tepat waktu dalam kondisi baik
    DIKEMBALIKAN_TERLAMBAT,     // Buku dikembalikan terlambat dalam kondisi baik
    DIKEMBALIKAN_RUSAK_RINGAN,  // Buku dikembalikan dengan kerusakan ringan
    DIKEMBALIKAN_RUSAK_BERAT,   // Buku dikembalikan dengan kerusakan berat
    HILANG                      // Buku dilaporkan hilang
}