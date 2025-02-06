//package com.example.biliosphere2.controller;
//
//import com.example.biliosphere2.dto.validasi.ValDendaDTO;
//import com.example.biliosphere2.service.DendaService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
///*
//IntelliJ IDEA 2024.3 (Ultimate Edition)
//Build #IU-243.21565.193, built on November 13, 2024
//@Author Dell Erlan Prambudi
//Java Developer
//Created on 2/4/2025 5:15 PM
//@Last Modified 2/4/2025 5:15 PM
//Version 1.0
//*/
//
//@RestController
//@RequestMapping("/denda")
//public class DendaController {
//
//    @Autowired
//    private DendaService dendaService;
//
//    Map<String,String> mapFilter = new HashMap<>();
//
//    public DendaController() {
//        filterColumnByMap();
//    }
//
//    @GetMapping("")
////    @PreAuthorize("hasAuthority('Denda')")
//    public ResponseEntity<Object> findAll(HttpServletRequest request){
//        Pageable pageable = PageRequest.of(0,10, Sort.by("id"));
//        return dendaService.findAll(pageable, request);
//    }
//
//    @PostMapping("")
////    @PreAuthorize("hasAuthority('Denda')")
//    public ResponseEntity<Object> save(@Valid @RequestBody ValDendaDTO denda, HttpServletRequest request){
//        return dendaService.save(denda, request);
//    }
//
//    @PutMapping("/{id}")
////    @PreAuthorize("hasAuthority('Denda')")
//    public ResponseEntity<Object> update(
//            @PathVariable(value = "id") Long id,
//            @Valid @RequestBody ValDendaDTO denda, HttpServletRequest request){
//        return dendaService.update(id, denda, request);
//    }
//
//    @DeleteMapping("/{id}")
////    @PreAuthorize("hasAuthority('Denda')")
//    public ResponseEntity<Object> delete(
//            @PathVariable(value = "id") Long id,
//            HttpServletRequest request){
//        return dendaService.delete(id, request);
//    }
//
//    @GetMapping("/{id}")
////    @PreAuthorize("hasAuthority('Denda')")
//    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id,
//                                           HttpServletRequest request){
//        return dendaService.findById(id, request);
//    }
//
//    @GetMapping("/{sort}/{sortBy}/{page}")
////    @PreAuthorize("hasAuthority('Denda')")
//    public ResponseEntity<Object> findByParam(
//            @PathVariable(value = "sort") String sort,
//            @PathVariable(value = "sortBy") String sortBy,
//            @PathVariable(value = "page") Integer page,
//            @RequestParam(value = "size") Integer size,
//            @RequestParam(value = "column") String column,
//            @RequestParam(value = "value") String value,
//            HttpServletRequest request){
//        Pageable pageable = null;
//        sortBy = mapFilter.get(sortBy) == null ? "id" : sortBy;
//        if (sort.equals("asc")) {
//            pageable = PageRequest.of(page, size, Sort.by(sortBy));
//        } else {
//            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
//        }
//        return dendaService.findByParam(pageable, column, value, request);
//    }
//
//    public void filterColumnByMap(){
//        mapFilter.put("status", "statusDenda");
//    }
//}
