package com.example.biliosphere2.dto.response;

import java.math.BigDecimal;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 4:56 AM
@Last Modified 2/4/2025 4:56 AM
Version 1.0
*/
public class RespBukuDTO {
    private Long id;
    private String judul;
    private String penulis;
    private String penerbit;
    private Integer tahunTerbit;
    private String namaKategori;
    private Integer stok;
    private BigDecimal harga;


    // Constructor kosong (penting buat Spring & ModelMapper)
    public RespBukuDTO() {}

    // Constructor buat setting ID dan Judul
    public RespBukuDTO(Long id, String judul) {
        this.id = id;
        this.judul = judul;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }
}
