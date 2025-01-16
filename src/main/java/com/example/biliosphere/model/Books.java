package com.example.biliosphere.model;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "MstBooks")
public class Books {
    @Id
    @Column(name = "BookId")
    private Long id;

    @Column(name = "Title", nullable = false, length = 255)
    private String judul;

    @Column(name = "Author", nullable = false, length = 100)
    private String penulis;

    @Column(name = "ISBN", nullable = false, unique = true)
    private String isbn;

    @Column(name = "PublishedYear")
    private Integer tahunTerbit;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity = 0;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false, insertable = false, updatable = false)
    private Categories category;

    @OneToMany(mappedBy = "book")
    private List<LoansDetail> loanDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(Integer tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public List<LoansDetail> getLoanDetails() {
        return loanDetails;
    }

    public void setLoanDetails(List<LoansDetail> loanDetails) {
        this.loanDetails = loanDetails;
    }
}
