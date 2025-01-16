package com.example.biliosphere.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/8/2025 2:40 AM
@Last Modified 1/8/2025 2:40 AM
Version 1.0
*/
@Entity
@Table(name = "MstUsers")
public class Users {
    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name", nullable = false)
    private String nama;

    @Column(name = "Address", nullable = false, length = 255)
    private String alamat;

    @Column(name = "PhoneNumber", nullable = false, length = 13)
    private String noTelpon;

    @Column(name = "Email", nullable = false, length = 50)
    private String email;

    @Column(name = "RegistrationDate", nullable = false)
    private LocalDate tanggalDaftar;

    @PrePersist
    protected void onCreate() {
        this.tanggalDaftar = LocalDate.now();
    }
//    gender in here
    @ManyToOne
    @JoinColumn(name = "GenderId", nullable = false)
    private Genders gender;

    @OneToMany(mappedBy = "user")
    private List<Loans> loans;

    @ManyToMany
    @JoinTable(
            name = "UserRoles",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId")
    )
    private Set<Roles> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelpon() {
        return noTelpon;
    }

    public void setNoTelpon(String noTelpon) {
        this.noTelpon = noTelpon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(LocalDate tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public Genders getGender() {
        return gender;
    }

    public void setGender(Genders gender) {
        this.gender = gender;
    }

    public List<Loans> getLoans() {
        return loans;
    }

    public void setLoans(List<Loans> loans) {
        this.loans = loans;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
