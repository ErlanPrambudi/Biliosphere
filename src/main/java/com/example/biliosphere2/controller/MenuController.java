package com.example.biliosphere2.controller;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 6:54 PM
@Last Modified 1/30/2025 6:54 PM
Version 1.0
*/


import com.example.biliosphere2.dto.validasi.ValMenuDTO;
import com.example.biliosphere2.model.Menu;
import com.example.biliosphere2.service.MenuService;
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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    Map<String,String> mapFilter = new HashMap<>();


    public MenuController() {
        filterColumnByMap();
    }

    @GetMapping("")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findAll(
            HttpServletRequest request){
        Pageable pageable = PageRequest.of(0,10, Sort.by("id"));//asc
        return menuService.findAll(pageable,request);
    }

    @PostMapping("")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValMenuDTO menu, HttpServletRequest request){
        return menuService.save(menuService.convertToMenu(menu),request);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ValMenuDTO menu, HttpServletRequest request){
        return menuService.update(id,menuService.convertToMenu(menu),request);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request){
        return menuService.delete(id,request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id,
                                           HttpServletRequest request){
        return menuService.findById(id,request);
    }

    @GetMapping("/{sort}/{sortBy}/{page}")
//    @PreAuthorize("hasAuthority('Menu')")
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
        return menuService.findByParam(pageable,column,value,request);
    }

    public void filterColumnByMap(){
        mapFilter.put("nama","nama");
        mapFilter.put("group","groupMenu");
    }
}

