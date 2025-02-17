package com.example.biliosphere2.dto.validasi;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 4:51 AM
@Last Modified 2/4/2025 4:51 AM
Version 1.0
*/
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public class ValBukuDTO {
    @NotBlank(message = "Judul tidak boleh kosong")
    @Size(max = 255, message = "Judul maksimal 255 karakter")
    private String judul;

    @NotBlank(message = "Penulis tidak boleh kosong")
    @Size(max = 100, message = "Penulis maksimal 100 karakter")
    private String penulis;

    @NotBlank(message = "Penerbit tidak boleh kosong")
    @Size(max = 100, message = "Penerbit maksimal 100 karakter")
    private String penerbit;

    @NotNull(message = "Tahun terbit tidak boleh kosong")
    @Min(value = 1900, message = "Tahun terbit minimal 1900")
    private Integer tahunTerbit;

    @NotNull(message = "Kategori tidak boleh kosong")
    private Long kategoriId;

    @NotNull(message = "Stok tidak boleh kosong")
    @Min(value = 0, message = "Stok minimal 0")
    private Integer stok;

    @NotNull(message = "Harga tidak boleh kosong")
    private BigDecimal harga;
    private MultipartFile file;

    // getter
    public MultipartFile getFile() {
        return file;
    }

    // setter
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public Integer getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(Integer tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public Long getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(Long kategoriId) {
        this.kategoriId = kategoriId;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }
}
