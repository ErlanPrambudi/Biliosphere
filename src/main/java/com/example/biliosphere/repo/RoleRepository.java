package com.example.biliosphere.repo;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/25/2025 1:00 PM
@Last Modified 1/25/2025 1:00 PM
Version 1.0
*/

import com.example.biliosphere.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles,String> {
    Optional<Roles> findByName(String name);
}
