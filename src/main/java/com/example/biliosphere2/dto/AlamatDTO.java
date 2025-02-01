package com.example.biliosphere2.dto;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/29/2025 10:05 PM
@Last Modified 1/29/2025 10:05 PM
Version 1.0
*/

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlamatDTO {

    private String jalan;
    private Integer nomor;
    private String kota;

    @JsonProperty("kode-pos")
    private Integer kodePos;

    public String getJalan() {
        return jalan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public Integer getNomor() {
        return nomor;
    }

    public void setNomor(Integer nomor) {
        this.nomor = nomor;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public Integer getKodePos() {
        return kodePos;
    }

    public void setKodePos(Integer kodePos) {
        this.kodePos = kodePos;
    }
}
