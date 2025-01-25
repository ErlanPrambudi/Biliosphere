package com.example.biliosphere.service;
import com.example.biliosphere.exception.ResourceNotFoundException;
import com.example.biliosphere.model.Roles;
import com.example.biliosphere.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/25/2025 1:08 PM
@Last Modified 1/25/2025 1:08 PM
Version 1.0
*/



@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Roles> findAll() {
        return roleRepository.findAll();
    }

    public Roles findById(String id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));
    }

    public Roles save(Roles role) {
        return roleRepository.save(role);
    }

    public Roles update(String id, Roles role) {
        Roles existingRole = findById(id);
        existingRole.setName(role.getName());
        return roleRepository.save(existingRole);
    }

    public void delete(String id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role with ID " + id + " not found");
        }
        roleRepository.deleteById(id);
    }
}