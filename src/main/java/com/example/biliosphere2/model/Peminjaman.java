package com.example.biliosphere2.model;

import com.example.biliosphere2.model.enums.StatusPembayaran;
import com.example.biliosphere2.model.enums.StatusPeminjamanEnum;
import com.example.biliosphere2.model.enums.StatusPengembalianEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MstPeminjaman")
public class Peminjaman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDUser", nullable = false, foreignKey = @ForeignKey(name = "fk_peminjaman_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDBuku", nullable = false, foreignKey = @ForeignKey(name = "fk_peminjaman_buku"))
    private Buku buku;

    @Column(name = "tanggalPinjam", nullable = false)
    private LocalDate tanggalPinjam;

    @Column(name = "tanggalKembali")
    private LocalDate tanggalKembali;

    @Enumerated(EnumType.STRING) // Simpan enum sebagai string di database
    @Column(name = "status_pengembalian")
    private StatusPengembalianEnum statusPengembalian;

    @Enumerated(EnumType.STRING) // Simpan enum sebagai string di database
    @Column(name = "status_peminjaman")
    private StatusPeminjamanEnum statusPeminjaman;

    @OneToOne(mappedBy = "peminjaman", cascade = CascadeType.ALL, orphanRemoval = true)
    private Denda denda;

    @Column(name = "status_pembayaran")
    @Enumerated(EnumType.STRING)
    private StatusPembayaran statusPembayaran;

    @Column(name = "createdBy", updatable = false, nullable = false)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "createdDate", updatable = false, nullable = false)
    private LocalDate createdDate;

    @Column(name = "updatedBy")
    private String updatedBy;

    @UpdateTimestamp
    @Column(name = "updatedDate")
    private LocalDate updatedDate;

    public Peminjaman() {
        this.statusPembayaran = StatusPembayaran.BELUM_DIBAYAR;
        this.statusPeminjaman = StatusPeminjamanEnum.DIAJUKAN;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public StatusPeminjamanEnum getStatusPeminjaman() {
        return statusPeminjaman;
    }

    public void setStatusPeminjaman(StatusPeminjamanEnum statusPeminjaman) {
        this.statusPeminjaman = statusPeminjaman;
    }

    public StatusPembayaran getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(StatusPembayaran statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(LocalDate tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public Denda getDenda() {
        return denda;
    }

    public void setDenda(Denda denda) {
        this.denda = denda;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public StatusPengembalianEnum getStatusPengembalian() {
        return statusPengembalian;
    }

    public void setStatusPengembalian(StatusPengembalianEnum statusPengembalian) {
        this.statusPengembalian = statusPengembalian;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }
}