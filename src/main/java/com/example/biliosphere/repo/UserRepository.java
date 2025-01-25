package com.example.biliosphere.repo;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/19/2025 8:55 PM
@Last Modified 1/19/2025 8:55 PM
Version 1.0
*/

import com.example.biliosphere.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,String> {
    boolean existsByEmail(String email);

//    public Page<Users> findByAlamatContainsIgnoreCase(org.springframework.data.domain.Pageable pageable, String nama);
//    public Page<Users> findByUsernameContainsIgnoreCase(Pageable pageable, String nama);
//    public Page<Users> findByEmailContainsIgnoreCase(Pageable pageable, String nama);
//    public Page<Users> findByNoHpContainsIgnoreCase(Pageable pageable, String nama);
//    public Page<Users> findByNamaContainsIgnoreCase(Pageable pageable, String nama);

}