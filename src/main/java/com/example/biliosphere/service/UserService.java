package com.example.biliosphere.service;

import com.example.biliosphere.model.Users;
import com.example.biliosphere.repo.UserRepository;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/20/2025 9:41 AM
@Last Modified 1/20/2025 9:41 AM
Version 1.0
*/
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public void save(Users u) {
//        Users u = new Users();
        u.setCreatedBy("erlan");
        userRepository.save(u);
    }
    //update function
    @Transactional
    public void update( String id,Users user) {
        Optional<Users> optionalUsers=userRepository.findById(id);
        if(!optionalUsers.isPresent()) {
            System.out.println("user not found");
        }
        Users userNext=optionalUsers.get();
        userNext.setNama(user.getNama());
        userNext.setAlamat(user.getAlamat());
        userNext.setNoTelpon(user.getNoTelpon());
        userNext.setEmail(user.getEmail());
        userNext.setTanggalDaftar(user.getTanggalDaftar());
        userNext.setUpdateBy("erlan");
        userNext.setUpdatedDate(LocalDate.now());
        Users u = new Users();
        userRepository.save(u);
    }
    //delete function
    @Transactional
    public void delete( String id) {
        try{
            Optional<Users> optionalUsers=userRepository.findById(id);
            if(!optionalUsers.isPresent()) {
                System.out.println("user not found");
            }
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
