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

@RestController
@RequestMapping("/peminjaman") // Ubah biar lebih RESTful
public class PeminjamanController {

    @Autowired
    private PeminjamanService peminjamanService;

    private final Map<String, String> mapFilter = new HashMap<>();

    public PeminjamanController() {
        filterColumnByMap();
    }

    // 🔹 1️⃣ Ambil semua peminjaman dengan pagination
    @GetMapping("")
//    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sort,
            HttpServletRequest request) {

        Pageable pageable = sort.equalsIgnoreCase("asc")
                ? PageRequest.of(page, size, Sort.by(sortBy))
                : PageRequest.of(page, size, Sort.by(sortBy).descending());

        return peminjamanService.findAll(pageable, request);
    }

    // 🔹 2️⃣ Tambah peminjaman
    @PostMapping("")
//    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        return peminjamanService.save(peminjamanDTO, request);
    }

    // 🔹 3️⃣ Update peminjaman (pengembalian buku)
    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ValPeminjamanDTO peminjamanDTO,
            HttpServletRequest request) {
        return peminjamanService.update(id, peminjamanDTO, request);
    }

    // 🔹 4️⃣ Hapus peminjaman berdasarkan ID
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request) {
        return peminjamanService.delete(id, request);
    }

    // 🔹 5️⃣ Ambil peminjaman berdasarkan ID
    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        return peminjamanService.findById(id, request);
    }

    // 🔹 6️⃣ Cari peminjaman berdasarkan parameter
    @GetMapping("/search")
//    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> searchPeminjaman(
            @RequestParam String columnName,
            @RequestParam String value,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sort,
            HttpServletRequest request) {

        sortBy = mapFilter.getOrDefault(sortBy, "id");

        Pageable pageable = sort.equalsIgnoreCase("asc")
                ? PageRequest.of(page, size, Sort.by(sortBy))
                : PageRequest.of(page, size, Sort.by(sortBy).descending());

        return peminjamanService.findByParam(pageable, columnName, value, request);
    }

    // 🔹 7️⃣ Mapping filter untuk pencarian data
    private void filterColumnByMap() {
        mapFilter.put("userNama", "user.userNama");
        mapFilter.put("bukuJudul", "buku.judul");
        mapFilter.put("statusPengembalian", "statusPengembalian");
        mapFilter.put("userId", "user.id");
        mapFilter.put("bukuId", "buku.id");
        mapFilter.put("statusPengembalianId", "statusPengembalian.id");
    }
}
