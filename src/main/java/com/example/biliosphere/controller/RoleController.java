package com.example.biliosphere.controller;

import com.example.biliosphere.model.Roles;
import com.example.biliosphere.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/25/2025 1:13 PM
@Last Modified 1/25/2025 1:13 PM
Version 1.0
*/
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Roles> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Roles getRoleById(@PathVariable String id) {
        return roleService.findById(id);
    }

    @PostMapping
    public Roles createRole(@RequestBody Roles role) {
        return roleService.save(role);
    }

    @PutMapping("/{id}")
    public Roles updateRole(@PathVariable String id, @RequestBody Roles role) {
        return roleService.update(id, role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}