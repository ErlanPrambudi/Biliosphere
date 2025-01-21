package com.example.biliosphere.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private String id;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false, insertable = false, updatable = false)
    private Users user;

    @Column(name = "LoanDate", nullable = false)
    private LocalDateTime loanDate;

    @Column(name = "DueDate", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "Status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "loan")
    private List<LoansDetail> loanDetails;

    @OneToMany(mappedBy = "loan")
    private List<Returns> returns;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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

    public List<LoansDetail> getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(List<LoansDetail> loanDetails) {
        this.loanDetails = loanDetails;
    }

    public List<Returns> getReturns() {
        return returns;
    }

    public void setReturns(List<Returns> returns) {
        this.returns = returns;
    }
}
