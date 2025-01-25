package com.example.biliosphere.repo;

import com.example.biliosphere.model.Genders;
import com.example.biliosphere.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/25/2025 3:55 PM
@Last Modified 1/25/2025 3:55 PM
Version 1.0
*/
@Repository
public interface GenderRepository extends JpaRepository<Genders,String> {
    boolean existsByGender(String gender);
}
