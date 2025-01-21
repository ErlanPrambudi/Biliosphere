package com.example.biliosphere.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "MstBooks")
public class Books {
    @Id
    @Column(name = "BookId")
    private String id;

    @Column(name = "Title", nullable = false, length = 255)
    private String judul;

    @Column(name = "Author", nullable = false, length = 100)
    private String penulis;

    @Column(name = "Publisher", nullable = false, length = 100)
    private String penerbit;

    @Column(name = "ISBN", nullable = false, unique = true)
    private String isbn;

    @Column(name = "PublishedYear")
    private Integer tahunTerbit;

    @Column(name = "Description")
    private String deskripsi;

    @Column(name = "Image", nullable = false)
    private String gambar;

    @Column(name = "Quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "CategoryId", nullable = false)
//   @JsonManagedReference
    private Categories category;

    @OneToMany(mappedBy = "book")
    private List<LoansDetail> loanDetails;


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
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
