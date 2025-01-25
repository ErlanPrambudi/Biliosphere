package com.example.biliosphere.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    @Column(name = "BookId")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "Name", nullable = false)
    private String nama;

    @Column(name = "Address", nullable = false, length = 255)
    private String alamat;

    @Column(name = "PhoneNumber", nullable = false, length = 13)
    private String noTelpon;

    @Column(name = "Email", nullable = false, length = 50)
    private String email;

    @Column(name = "Password", nullable = false, length = 50)
    private String password;


    @Column(name = "RegistrationDate", nullable = false)
    private LocalDate tanggalDaftar;

    @PrePersist
    protected void onCreate() {
        this.tanggalDaftar = LocalDate.now();
    }
//    gender in here
    @ManyToOne
    @JoinColumn(name = "GenderId")
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

    @Column(name = "CreatedBy",updatable = false)
    private String createdBy;

    @Column(name = "CreatedDate",updatable = false)
    private LocalDate createdDate =LocalDate.now();

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

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public LocalDate getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDate updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Column(name = "UpdateBy",insertable = false)
    private String updateBy;

    @Column(name = "UpdatedDate",insertable = false)
    private LocalDate updatedDate = LocalDate.now();

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
