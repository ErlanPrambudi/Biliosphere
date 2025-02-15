package com.example.biliosphere2.controller;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/15/2025 12:39 AM
@Last Modified 2/15/2025 12:39 AM
Version 1.0
*/

import com.example.biliosphere2.model.enums.StatusPengembalianEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/enum")
public class EnumController {

    // ✅ Endpoint untuk mendapatkan semua status pengembalian
    @GetMapping("/status-pengembalian")
    public ResponseEntity<List<String>> getStatusPengembalianEnum() {
        List<String> statusList = Arrays.stream(StatusPengembalianEnum.values())
                .map(Enum::name)
                .toList();
        return ResponseEntity.ok(statusList);
    }
}
