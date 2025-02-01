package com.example.biliosphere2.repository;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 6:44 PM
@Last Modified 1/30/2025 6:44 PM
Version 1.0
*/

import com.example.biliosphere2.model.GroupMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMenuRepo extends JpaRepository<GroupMenu, Long> {

    //DERIVED query
    //select * from MstGroupMenu WHERE NamaGroupMenu LIKE toLower('%?%')
    public Page<GroupMenu> findByNamaGroupMenuContainsIgnoreCase(Pageable pageable, String nama);

    // UNTUK REPORT
    public List<GroupMenu> findByNamaGroupMenuContainsIgnoreCase(String nama);
    public Optional<GroupMenu> findTopByOrderByIdDesc();

}