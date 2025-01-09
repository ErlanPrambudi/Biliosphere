package com.example.biliosphere.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "MstBooks")
public class Books {
    @Id
    @Column(name = "BookId")
    private Long id;

    @Column(name = "Title ",nullable = false,length = 255)
    private String judul;

    @Column(name = "Author",nullable = false,length = 100)
    private String penulis;

    @Column(name = "CategoryId",nullable = false)
    private Integer idKategori;

    @Column(name = "ISBN",nullable = false,unique = true)
    private String isbn;

    @Column(name = "PublishedYear")
    private Integer tahunTerbit;

    @Column(name = "Quantity",nullable = false)
    private Integer quantity = 0;

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

    public Integer getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(Integer idKategori) {
        this.idKategori = idKategori;
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
}
