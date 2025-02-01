package com.example.biliosphere2.repository;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 6:36 PM
@Last Modified 1/30/2025 6:36 PM
Version 1.0
*/
import com.example.biliosphere2.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepo extends JpaRepository<Menu,Long> {

    //DERIVED query
    //select * from MstGroupMenu WHERE NamaGroupMenu LIKE toLower('%?%')
    public Page<Menu> findByNamaContainsIgnoreCase(Pageable pageable, String nama);

    // UNTUK REPORT
    public List<Menu> findByNamaContainsIgnoreCase(String nama);

    @Query(value = "SELECT m FROM Menu m WHERE lower(m.groupMenu.namaGroupMenu) LIKE lower(concat('%',?1,'%'))")
    public Page<Menu> cariGroupMenu(Pageable pageable, String nama);

    @Query(value = "SELECT m FROM Menu m WHERE lower(m.groupMenu.namaGroupMenu) LIKE lower(concat('%',?1,'%'))")
    public List<Menu> cariGroupMenu(String nama);

    public Optional<Menu> findTopByOrderByIdDesc();
}
