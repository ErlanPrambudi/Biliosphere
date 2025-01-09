package com.example.biliosphere.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/8/2025 11:48 PM
@Last Modified 1/8/2025 11:48 PM
Version 1.0
*/
@Entity
@Table(name = "MstLoansDetail")
public class LoansDetail {
    @Id
    @Column(name = "LoanDetailId")
    private Long id;

    @Column(name = "LoanID",nullable = false)
    private Integer idLoan;

    @Column(name = "BookId",nullable = false)
    private Integer idBook;

    @Column(name = "Quantity",nullable = false)
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(Integer idLoan) {
        this.idLoan = idLoan;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idBook) {
        this.idBook = idBook;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

