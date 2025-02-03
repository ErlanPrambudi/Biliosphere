package com.example.biliosphere2.dto.validasi;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 12:49 AM
@Last Modified 2/4/2025 12:49 AM
Version 1.0
*/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ValKategoriDTO {

    @NotBlank(message = "Nama kategori tidak boleh kosong")
    @Size(max = 50, message = "Nama kategori maksimal 50 karakter")
    private String namaKategori;

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
}
