package com.example.biliosphere.model;

import jakarta.persistence.*;

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
    private String id;

    @ManyToOne
    @JoinColumn(name = "LoanID", nullable = false, insertable = false, updatable = false)
    private Loans loan;

    @ManyToOne
    @JoinColumn(name = "BookId", nullable = false, insertable = false, updatable = false)
    private Books book;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false, insertable = false, updatable = false)
    private Users users;


    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Loans getLoan() {
        return loan;
    }

    public void setLoan(Loans loan) {
        this.loan = loan;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

