package com.example.biliosphere2.controller;

import com.example.biliosphere2.dto.validasi.ValKategoriDTO;
import com.example.biliosphere2.service.KategoriService;
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
@RequestMapping("/kategori")
public class KategoriController {

    @Autowired
    private KategoriService kategoriService;

    Map<String,String> mapFilter = new HashMap<>();

    public KategoriController() {
        filterColumnByMap();
    }

    @GetMapping("")
    public ResponseEntity<Object> findAll(HttpServletRequest request){
        Pageable pageable = PageRequest.of(0,10, Sort.by("id"));
        return kategoriService.findAll(pageable,request);
    }

    @PostMapping("")
//    @PreAuthorize("hasAuthority('Kategori')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValKategoriDTO kategori, HttpServletRequest request){
        return kategoriService.save(kategori,request);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('Kategori')")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ValKategoriDTO kategori, HttpServletRequest request){
        return kategoriService.update(id,kategori,request);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('Kategori')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request){
        return kategoriService.delete(id,request);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('Kategori')")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id,
                                           HttpServletRequest request){
        return kategoriService.findById(id,request);
    }

    @GetMapping("/{sort}/{sortBy}/{page}")
//    @PreAuthorize("hasAuthority('Kategori')")
    public ResponseEntity<Object> findByParam(
            @PathVariable(value = "sort") String sort,
            @PathVariable(value = "sortBy") String sortBy,
            @PathVariable(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "column") String column,
            @RequestParam(value = "value") String value,
            HttpServletRequest request){
        Pageable pageable = null;
        sortBy = mapFilter.get(sortBy)==null?"id":sortBy;
        if(sort.equals("asc")){
            pageable = PageRequest.of(page,size, Sort.by(sortBy));
        }else {
            pageable = PageRequest.of(page,size, Sort.by(sortBy).descending());
        }
        return kategoriService.findByParam(pageable,column,value,request);
    }

    public void filterColumnByMap(){
        mapFilter.put("nama","namaKategori");
    }
}