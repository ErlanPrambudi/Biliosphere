package com.example.biliosphere2.controller;

import com.example.biliosphere2.dto.validasi.ValGroupMenuDTO;
import com.example.biliosphere2.service.GroupMenuService;
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
@RequestMapping("/group-menu")
public class GroupMenuController {

    @Autowired
    private GroupMenuService groupMenuService;

    Map<String,String> mapFilter = new HashMap<>();


    public GroupMenuController() {
        filterColumnByMap();
    }

    @GetMapping("")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findAll(
            HttpServletRequest request){
        Pageable pageable = PageRequest.of(0,10, Sort.by("id"));//asc
        return groupMenuService.findAll(pageable,request);
    }

    @PostMapping("")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> save(@Valid @RequestBody ValGroupMenuDTO groupMenu, HttpServletRequest request){
        return groupMenuService.save(groupMenuService.convertToListRespGroupMenuDTO(groupMenu), request);
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody ValGroupMenuDTO groupMenu, HttpServletRequest request){
        return groupMenuService.update(id,groupMenuService.convertToListRespGroupMenuDTO(groupMenu),request);
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Long id,
            HttpServletRequest request){
        return groupMenuService.delete(id,request);
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('Menu')")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id,
                                           HttpServletRequest request){
        return groupMenuService.findById(id,request);
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
        return groupMenuService.findByParam(pageable,column,value,request);
    }

    public void filterColumnByMap(){
        mapFilter.put("nama","nama");
        mapFilter.put("path","/path");
        mapFilter.put("group-menu","group-menu");
    }
}

