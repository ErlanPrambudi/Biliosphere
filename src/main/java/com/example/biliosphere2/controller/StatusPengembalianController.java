package com.example.biliosphere2.controller;

import com.example.biliosphere2.service.StatusPengembalianService;
import com.example.biliosphere2.dto.validasi.ValStatusPengembalianDTO;
import com.example.biliosphere2.dto.response.RespStatusPengembalianDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/status-pengembalian")
@Validated
public class StatusPengembalianController {

    @Autowired
    private StatusPengembalianService statusPengembalianService;

    // Endpoint to save a new status
    @PostMapping("")
    @PreAuthorize("hasAuthority('Status-pengembalian')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValStatusPengembalianDTO dto, HttpServletRequest request) {
        return statusPengembalianService.save(dto, request);
    }

    // Endpoint to update an existing status by ID
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Status-pengembalian')")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody ValStatusPengembalianDTO dto, HttpServletRequest request) {
        return statusPengembalianService.update(id, dto, request);
    }

    // Endpoint to delete a status by ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Status-pengembalian')")
    public ResponseEntity<Object> delete(@PathVariable Long id, HttpServletRequest request) {
        return statusPengembalianService.delete(id, request);
    }

    // Endpoint to get all statuses with pagination
    @GetMapping
    @PreAuthorize("hasAuthority('Status-pengembalian')")
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        // Default pagination if no params are passed (using PageRequest with default values)
        if (pageable == null) {
            pageable = PageRequest.of(0, 10); // Default to first page, 10 records
        }
        return statusPengembalianService.findAll(pageable, request);
    }

    // Endpoint to get a specific status by ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id, HttpServletRequest request) {
        return statusPengembalianService.findById(id, request);
    }

    // Endpoint to search statuses by a parameter (status name or part of it)
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('Status-pengembalian')")
    public ResponseEntity<Object> findByParam(@RequestParam String value, Pageable pageable, HttpServletRequest request) {
        // Default pagination if no params are passed (using PageRequest with default values)
        if (pageable == null) {
            pageable = PageRequest.of(0, 10); // Default to first page, 10 records
        }
        return statusPengembalianService.findByParam(pageable, value, request);
    }
}
