package com.example.biliosphere2.controller;

import com.example.biliosphere2.dto.validasi.ValPeminjamanDTO;
import com.example.biliosphere2.service.PeminjamanService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/peminjaman")
@Validated
public class PeminjamanController {

    @Autowired
    private PeminjamanService peminjamanService;

    // ✅ Simpan peminjaman baru
    @PostMapping("")
    public ResponseEntity<Object> save(@Valid @RequestBody ValPeminjamanDTO dto, HttpServletRequest request) {
        return peminjamanService.save(dto, request);
    }

    // ✅ Update peminjaman (termasuk update status pengembalian)
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ValPeminjamanDTO dto, HttpServletRequest request) {
        return peminjamanService.update(id, dto, request);
    }

    // ✅ Hapus peminjaman
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        return peminjamanService.delete(id, request);
    }

    // ✅ Get semua peminjaman dengan pagination
    @GetMapping
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10);
        }
        return peminjamanService.findAll(pageable, request);
    }

    // ✅ Get peminjaman berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id, HttpServletRequest request) {
        return peminjamanService.findById(id, request);
    }

    // ✅ Get peminjaman berdasarkan parameter (contoh: berdasarkan status pengembalian)
    @GetMapping("/search")
    public ResponseEntity<Object> findByParam(@RequestParam String value, Pageable pageable, HttpServletRequest request) {
        if (pageable == null) {
            pageable = PageRequest.of(0, 10);
        }
        return peminjamanService.findByParam(pageable, "statusPengembalian", value, request);
    }
}
