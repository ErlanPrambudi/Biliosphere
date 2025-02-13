//package com.example.biliosphere2.controller;
//
//import com.example.biliosphere2.dto.response.RespDendaDTO;
//import com.example.biliosphere2.model.Peminjaman;
//import com.example.biliosphere2.service.DendaService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/denda")
//public class DendaController {
//
//    @Autowired
//    private DendaService dendaService;
//
//    @PostMapping("")
//    @PreAuthorize("hasAuthority('Denda')")
//    public ResponseEntity<?> hitungDenda(@Valid @RequestBody Peminjaman peminjaman, HttpServletRequest request) {
//        String dibuatOleh = request.getUserPrincipal().getName(); // Mendapatkan user yang sedang login
//        RespDendaDTO respDenda = dendaService.hitungDenda(peminjaman, dibuatOleh);
//        return ResponseEntity.ok(respDenda);
//    }
//}
