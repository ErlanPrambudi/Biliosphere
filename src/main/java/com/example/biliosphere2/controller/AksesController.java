package com.example.biliosphere2.controller;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/28/2025 8:19 PM
@Last Modified 1/28/2025 8:19 PM
Version 1.0
*/

import com.example.biliosphere2.dto.validasi.ValAksesDTO;
import com.example.biliosphere2.service.AksesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@RequestMapping("/akses")
public class AksesController {

    @Autowired
    private AksesService aksesService;

    Map<String,String> mapFilter = new HashMap<>();


    public AksesController() {
        filterColumnByMap();
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> findAll(
            HttpServletRequest request){
        Pageable pageable = PageRequest.of(0,10, Sort.by("id"));//asc
        return aksesService.findAll(pageable,request);
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValAksesDTO aksesDTO, HttpServletRequest request){
        return aksesService.save(aksesService.convertToAkses(aksesDTO),request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ValAksesDTO aksesDTO, HttpServletRequest request){
        return aksesService.update(id, aksesService.convertToAkses(aksesDTO),request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request){
        return aksesService.delete(id,request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id,
                                           HttpServletRequest request){
        return aksesService.findById(id,request);
    }

    @GetMapping("/{sort}/{sortBy}/{page}")
    @PreAuthorize("hasAuthority('Akses')")
    public ResponseEntity<Object> findByParam(
            @PathVariable(value = "sort") String sort,
            @PathVariable(value = "sortBy") String sortBy,//name
            @PathVariable(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "column") String column,
            @RequestParam(value = "value") String value,
            HttpServletRequest request){
        Pageable pageable = null;
        sortBy = mapFilter.get(sortBy)==null?"id":sortBy;
        if(sort.equals("asc")){
            pageable = PageRequest.of(page,size, Sort.by(sortBy));//asc
        }else {
            pageable = PageRequest.of(page,size, Sort.by(sortBy).descending());//asc
        }
        return aksesService.findByParam(pageable,column,value,request);
    }


    public void filterColumnByMap(){
        mapFilter.put("nama","nama");
    }
}

