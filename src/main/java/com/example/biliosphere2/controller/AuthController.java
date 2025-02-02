package com.example.biliosphere2.controller;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 5:57 PM
@Last Modified 1/30/2025 5:57 PM
Version 1.0
*/


import com.example.biliosphere2.dto.report.ReportDTO;
import com.example.biliosphere2.dto.validasi.ValRegisDTO;
import com.example.biliosphere2.dto.validasi.ValLoginDTO;
import com.example.biliosphere2.dto.validasi.ValVerifyRegisDTO;
import com.example.biliosphere2.service.AppUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthController {
    /**
     * LOGIN
     * REGISTRASI
     * FORGOT PASSWORD
     */

    @Autowired
    AppUserDetailService appUserDetailService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody ValLoginDTO valLoginDTO,
                                        HttpServletRequest request) {
        return appUserDetailService.login(appUserDetailService.convertToUser(valLoginDTO),request);
    }

    @PostMapping("/regis")
    public ResponseEntity<Object> register(@Valid @RequestBody ValRegisDTO regisDTO, HttpServletRequest request){
        return appUserDetailService.regis(appUserDetailService.convertToUser(regisDTO),request);
    }

    @PostMapping("/verify-regis")
    public ResponseEntity<Object> verifyRegister(@Valid @RequestBody ValVerifyRegisDTO valVerifyRegisDTO, HttpServletRequest request){
        return appUserDetailService.verifyRegis(appUserDetailService.convertToUser(valVerifyRegisDTO),request);
    }


}