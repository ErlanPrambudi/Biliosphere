package com.example.biliosphere.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/8/2025 5:14 PM
@Last Modified 1/8/2025 5:14 PM
Version 1.0
*/
@Entity
@Table(name = "MstLoans")
public class Loans {
    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "UserId",nullable = false)
    private Integer idUser;

    @Column(name = "LoanDate", nullable = false)
    private LocalDateTime loanDate;

    @Column(name = "DueDate", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "Status",nullable = false)
    private String status;

    public Long  getId() {
        return id;
    }

    public void setId(Long  id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
