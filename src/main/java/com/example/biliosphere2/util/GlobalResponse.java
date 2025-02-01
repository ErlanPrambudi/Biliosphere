package com.example.biliosphere2.util;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/28/2025 7:56 PM
@Last Modified 1/28/2025 7:56 PM
Version 1.0
*/

import com.example.biliosphere2.handler.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class GlobalResponse {

    public static ResponseEntity<Object> dataTidakValid(
            String errorCode,
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("Data Tidak Valid !",
                HttpStatus.BAD_REQUEST,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> dataGagalDisimpan(
            String errorCode,
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("DATA GAGAL DISIMPAN",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> dataGagalDiubah(
            String errorCode,
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("DATA GAGAL DIUBAH",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> dataGagalDihapus(
            String errorCode,
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("DATA GAGAL DIHAPUS",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,errorCode,request);
    }

    public static ResponseEntity<Object> dataBerhasilDisimpan(
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("DATA BERHASIL DISIMPAN",
                HttpStatus.CREATED,
                null,null,request);
    }

    public static ResponseEntity<Object> dataBerhasilDiubah(
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("DATA BERHASIL DIUBAH",
                HttpStatus.OK,
                null,null,request);
    }

    public static ResponseEntity<Object> dataBerhasilDihapus(
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("DATA BERHASIL DIHAPUS",
                HttpStatus.OK,
                null,null,request);
    }

    public static ResponseEntity<Object> dataTidakDitemukan(
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("DATA TIDAK DITEMUKAN",
                HttpStatus.OK,
                null,"X01001",request);
    }

    public static ResponseEntity<Object> dataResponseList(
            Map<String,Object> map ,
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("OK",
                HttpStatus.OK,
                map,null,request);
    }

    public static ResponseEntity<Object> dataResponseObject(
            Object object,
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("OK",
                HttpStatus.OK,
                object,null,request);
    }

    public static ResponseEntity<Object> dataGagalDiakses(
            String errorCode,
            HttpServletRequest request
    ){
        return new ResponseHandler().handleResponse("DATA GAGAL DIAKSES",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,errorCode,request);
    }
}
