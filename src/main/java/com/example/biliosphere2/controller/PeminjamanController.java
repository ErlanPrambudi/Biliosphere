package com.example.biliosphere2.controller;

import com.example.biliosphere2.dto.validasi.ValPeminjamanDTO;
import com.example.biliosphere2.service.PeminjamanService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 6:56 PM
@Last Modified 2/4/2025 6:56 PM
Version 1.0
*/


@RestController
@RequestMapping("/peminjaman")
public class PeminjamanController {

    @Autowired
    private PeminjamanService peminjamanService;

    Map<String, String> mapFilter = new HashMap<>();

    public PeminjamanController() {
        filterColumnByMap();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        return peminjamanService.findAll(pageable, request);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        return peminjamanService.save(peminjamanDTO, request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        return peminjamanService.update(id, peminjamanDTO, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request) {
        return peminjamanService.delete(id, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        return peminjamanService.findById(id, request);
    }

    @GetMapping("/{sort}/{sortBy}/{page}")
    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> findByParam(
            @PathVariable(value = "sort") String sort,
            @PathVariable(value = "sortBy") String sortBy,
            @PathVariable(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "column") String column,
            @RequestParam(value = "value") String value,
            HttpServletRequest request) {
        Pageable pageable = null;
        sortBy = mapFilter.get(sortBy) == null ? "id" : sortBy;
        if (sort.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy));
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }
        return peminjamanService.findByParam(pageable, column, value, request);
    }

    public void filterColumnByMap() {
        mapFilter.put("userNama", "userNama");
        mapFilter.put("bukuJudul", "bukuJudul");
        mapFilter.put("statusPengembalian", "statusPengembalian");
    }
}

