package com.example.biliosphere2.dto.validasi;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import java.util.Date;
/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 4:37 PM
@Last Modified 2/4/2025 4:37 PM
Version 1.0
*/

public class ValDendaDTO {
    @NotNull(message = "ID Peminjaman tidak boleh kosong")
    private Long peminjamanId;

    @NotNull(message = "Jumlah denda tidak boleh kosong")
    @Min(value = 0, message = "Jumlah denda tidak boleh kurang dari 0")
    private Double jumlahDenda;

    @NotNull(message = "Tanggal denda tidak boleh kosong")
    private Date tanggalDenda;

    @NotNull(message = "Status pembayaran tidak boleh kosong")
    private Boolean statusPembayaran;

    // Getters and Setters
    public Long getPeminjamanId() {
        return peminjamanId;
    }

    public void setPeminjamanId(Long peminjamanId) {
        this.peminjamanId = peminjamanId;
    }

    public Double getJumlahDenda() {
        return jumlahDenda;
    }

    public void setJumlahDenda(Double jumlahDenda) {
        this.jumlahDenda = jumlahDenda;
    }

    public Date getTanggalDenda() {
        return tanggalDenda;
    }

    public void setTanggalDenda(Date tanggalDenda) {
        this.tanggalDenda = tanggalDenda;
    }

    public Boolean getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(Boolean statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }
}
