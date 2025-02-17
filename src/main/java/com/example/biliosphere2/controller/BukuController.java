package com.example.biliosphere2.controller;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 5:20 AM
@Last Modified 2/4/2025 5:20 AM
Version 1.0
*/


import com.example.biliosphere2.dto.validasi.ValBukuDTO;
import com.example.biliosphere2.service.BukuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/buku")
public class BukuController {

    @Autowired
    private BukuService bukuService;

    Map<String, String> mapFilter = new HashMap<>();

    public BukuController() {
        filterColumnByMap();
    }

    @GetMapping("")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        return bukuService.findAll(pageable, request);
    }

    @PostMapping("")
//    @PreAuthorize("hasAuthority('Buku')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValBukuDTO buku, HttpServletRequest request) {
        return bukuService.save(buku, request);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('Buku')")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ValBukuDTO buku,
            HttpServletRequest request) {
        return bukuService.update(id, buku, request);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('Buku')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request) {
        return bukuService.delete(id, request);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('Buku')")
    public ResponseEntity<Object> findById(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request) {
        return bukuService.findById(id, request);
    }

    @GetMapping("/{sort}/{sortBy}/{page}")
//    @PreAuthorize("hasAuthority('Buku')")
    public ResponseEntity<Object> findByParam(
            @PathVariable(value = "sort") String sort,
            @PathVariable(value = "sortBy") String sortBy,
            @PathVariable(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "column") String column,
            @RequestParam(value = "value") String value,
            HttpServletRequest request) {

        Pageable pageable;
        sortBy = mapFilter.get(sortBy) == null ? "id" : sortBy;

        if (sort.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy));
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        return bukuService.findByParam(pageable, column, value, request);
    }
    // ✅ Endpoint baru untuk upload gambar buku
    @PostMapping("/upload/{judul}")
    public ResponseEntity<Object> uploadImage(
            @PathVariable String judul,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) {
        return bukuService.uploadImage(judul, file, request); // Kirim langsung MultipartFile
    }

    public void filterColumnByMap() {
        mapFilter.put("judul", "judul");
        mapFilter.put("penulis", "penulis");
        mapFilter.put("penerbit", "penerbit");
        mapFilter.put("tahunTerbit", "tahunTerbit");
        mapFilter.put("kategori", "namaKategori");
    }
}
