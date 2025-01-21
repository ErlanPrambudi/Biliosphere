package com.example.biliosphere.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "MstCategory")
public class Categories {

    @Id
    @Column(name = "CategoryId")
    private String id;

    @Column(name = "CategoryName", unique = true, nullable = false, length = 20)
    private String namaCategory;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL, orphanRemoval = true )
    @JsonBackReference
    private List<Books> books;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
