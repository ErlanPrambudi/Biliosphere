package com.example.biliosphere.repo;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/20/2025 3:25 PM
@Last Modified 1/20/2025 3:25 PM
Version 1.0
*/

import com.example.biliosphere.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoansRepository extends JpaRepository<Loans, String> {
}
