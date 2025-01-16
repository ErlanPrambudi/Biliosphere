package com.example.biliosphere.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/8/2025 11:54 PM
@Last Modified 1/8/2025 11:54 PM
Version 1.0
*/
@Entity
@Table(name = "MstReturns")
public class Returns {
    @Id
    @Column(name = "ReturnId")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LoanID", nullable = false, insertable = false, updatable = false)
    private Loans loan;

    @Column(name = "ReturnDate", nullable = false)
    private LocalDate returnDate;

    @Column(name = "Fine", nullable = false, precision = 10, scale = 2)
    private BigDecimal fine = BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Loans getLoan() {
        return loan;
    }

    public void setLoan(Loans loan) {
        this.loan = loan;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }
}
