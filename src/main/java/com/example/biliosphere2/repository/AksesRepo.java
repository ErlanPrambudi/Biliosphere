package com.example.biliosphere2.repository;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 2:51 PM
@Last Modified 1/30/2025 2:51 PM
Version 1.0
*/

import com.example.biliosphere2.model.Akses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AksesRepo extends JpaRepository<Akses,Long> {

    //DERIVED query
    //select * from MstAkses WHERE NamaGroupMenu LIKE toLower('%?%')
    public Page<Akses> findByNamaContainsIgnoreCase(Pageable pageable, String nama);

    // UNTUK REPORT
    public List<Akses> findByNamaContainsIgnoreCase(String nama);
}
