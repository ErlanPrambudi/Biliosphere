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
@RequestMapping("/peminjaman")
public class PeminjamanController {

    @Autowired
    private PeminjamanService peminjamanService;

    private final Map<String, String> mapFilter = new HashMap<>();

    public PeminjamanController() {
        filterColumnByMap();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('Peminjaman')")
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

    @PostMapping("")
    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        return peminjamanService.save(peminjamanDTO, request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Peminjaman')")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ValPeminjamanDTO peminjamanDTO,
            HttpServletRequest request) {
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

        sortBy = mapFilter.getOrDefault(sortBy, "id");

        Pageable pageable = sort.equalsIgnoreCase("asc")
                ? PageRequest.of(page, size, Sort.by(sortBy))
                : PageRequest.of(page, size, Sort.by(sortBy).descending());

        return peminjamanService.findByParam(pageable, column, value, request);
    }

    public void filterColumnByMap() {
        mapFilter.put("userNama", "user.userNama");
        mapFilter.put("bukuJudul", "buku.judul");
        mapFilter.put("statusPengembalian", "statusPengembalian");
        mapFilter.put("userId", "user.id");
        mapFilter.put("bukuId", "buku.id");
        mapFilter.put("statusPengembalianId", "statusPengembalian.id");
    }
}