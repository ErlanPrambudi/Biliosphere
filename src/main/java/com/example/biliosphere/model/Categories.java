package com.example.biliosphere.model;



import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "MstCategory")
public class Categories {

    @Id
    @Column(name = "CategoryId")
    private Long id;

    @Column(name = "CategoryName", unique = true, nullable = false, length = 20)
    private String namaCategory;

    @OneToMany(mappedBy = "category")
    private List<Books> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaCategory() {
        return namaCategory;
    }

    public void setNamaCategory(String namaCategory) {
        this.namaCategory = namaCategory;
    }

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }
}
