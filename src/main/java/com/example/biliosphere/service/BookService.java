package com.example.biliosphere.service;

import com.example.biliosphere.exception.ResourceNotFoundException;
import com.example.biliosphere.exception.BadRequestException;
import com.example.biliosphere.model.Books;
import com.example.biliosphere.model.Categories;
import com.example.biliosphere.repo.BookRepository;
import com.example.biliosphere.repo.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // Mengambil semua buku
    public List<Books> findAll() {
        return bookRepository.findAll();
    }

    // Mengambil buku berdasarkan ID
    public Books findById(String id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("book with id " + id + " not found"));
    }

    // Menyimpan buku baru
    @Transactional
    public Books save(Books book) {
        if (StringUtils.isEmpty(book.getJudul())) {
            throw new BadRequestException("Judul buku tidak boleh kosong");
        }
        if (book.getCategory() == null || !StringUtils.hasText(book.getCategory().getId())) {
            throw new BadRequestException("Kategori dan ID kategori buku tidak boleh kosong");
        }
        book.setId(UUID.randomUUID().toString()); // Menetapkan ID unik untuk buku
        return bookRepository.save(book);
    }

    @Transactional
    public Books update(String id, Books book) {
        // Memeriksa apakah buku dengan ID tersebut ada
        Books existingBook = findById(id); // Jika tidak ada, akan melempar ResourceNotFoundException

        // Validasi judul
        if (StringUtils.isEmpty(book.getJudul())) {
            throw new BadRequestException("Judul buku tidak boleh kosong");
        }

        // Validasi kategori
        if (book.getCategory() == null || !StringUtils.hasText(book.getCategory().getId())) {
            throw new BadRequestException("Kategori dan ID kategori buku tidak boleh kosong");
        }

        // Memeriksa apakah kategori berbeda dari kategori yang sudah ada
        if (!existingBook.getCategory().getId().equals(book.getCategory().getId())) {
            // Validasi keberadaan kategori di database
            Optional<Categories> categoryOptional = categoryRepository.findById(book.getCategory().getId());
            if (categoryOptional.isEmpty()) {
                throw new BadRequestException("Kategori dengan ID yang diberikan tidak ditemukan");
            }
        }

        // Validasi atribut lainnya
        if (StringUtils.isEmpty(book.getPenulis())) {
            throw new BadRequestException("Penulis buku tidak boleh kosong");
        }
        if (StringUtils.isEmpty(book.getPenerbit())) {
            throw new BadRequestException("Penerbit buku tidak boleh kosong");
        }

        // Memperbarui atribut buku yang ditemukan
        existingBook.setJudul(book.getJudul());
        existingBook.setPenulis(book.getPenulis());
        existingBook.setPenerbit(book.getPenerbit());
        existingBook.setCategory(book.getCategory()); // Mengubah kategori
        existingBook.setDeskripsi(book.getDeskripsi());
        existingBook.setTahunTerbit(book.getTahunTerbit());
        existingBook.setGambar(book.getGambar());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setQuantity(book.getQuantity());

        // Menyimpan data buku yang diperbarui
        return bookRepository.save(existingBook);
    }


    // Memperbarui gambar buku berdasarkan ID
    @Transactional
    public Books updateImage(String id, String image) {
        Books book = findById(id);
        book.setGambar(image);
        return bookRepository.save(book);
    }

    // Menghapus buku berdasarkan ID
    @Transactional
    public void delete(String id) {
        Optional<Books> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            throw new ResourceNotFoundException("Book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
}
