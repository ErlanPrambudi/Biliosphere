package com.example.biliosphere.controller;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/20/2025 4:37 PM
@Last Modified 1/20/2025 4:37 PM
Version 1.0
*/

import com.example.biliosphere.exception.BadRequestException;
import com.example.biliosphere.model.Books;
import com.example.biliosphere.repo.CategoryRepository;
import com.example.biliosphere.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BookService bookService;

    @GetMapping("/book")
    public List<Books> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/book/{id}")
    public Books findById(@PathVariable("id") String id) {
        return bookService.findById(id);
    }

    @PostMapping("/book")
    public Books save(@RequestBody Books book) {
        if (book.getId() != null) {
            throw new IllegalArgumentException("ID should not be provided for creating a new book");
        }
        if (!StringUtils.hasText(book.getJudul())) {
            throw new BadRequestException("Judul buku tidak boleh kosong");
        }
        if (!StringUtils.hasText(book.getPenulis())) {
            throw new BadRequestException("Penulis buku tidak boleh kosong");
        }
        if (!StringUtils.hasText(book.getPenerbit())) {
            throw new BadRequestException("Penerbit buku tidak boleh kosong");
        }
        if (!StringUtils.hasText(book.getIsbn())) {
            throw new BadRequestException("ISBN buku tidak boleh kosong");
        }
        if (!StringUtils.hasText(book.getGambar())) {
            throw new BadRequestException("Gambar buku tidak boleh kosong");
        }
        if (book.getQuantity() == null || book.getQuantity() <= 0) {
            throw new BadRequestException("Quantity buku tidak boleh kosong dan harus lebih besar dari 0");
        }

        // Validasi kategori buku
        if (book.getCategory() == null || !StringUtils.hasText(book.getCategory().getId())) {
            throw new BadRequestException("ID kategori buku tidak boleh kosong");
        }

        // Verifikasi kategori
        categoryRepository.findById(book.getCategory().getId())
                .orElseThrow(() -> new BadRequestException("Category id " + book.getCategory().getId() + " tidak ditemukan"));

        return bookService.save(book);
    }

    @PutMapping("/book/{id}")
    public void update(@PathVariable("id") String id, @RequestBody Books book) {
        bookService.update(id, book);
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable("id") String id) {
        bookService.delete(id);
    }
}