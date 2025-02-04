package com.example.biliosphere2.service;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 6:23 PM
@Last Modified 2/4/2025 6:23 PM
Version 1.0
*/


import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.core.IService;
import com.example.biliosphere2.dto.response.RespPeminjamanDTO;
import com.example.biliosphere2.dto.validasi.ValPeminjamanDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.Peminjaman;
import com.example.biliosphere2.repository.PeminjamanRepo;
import com.example.biliosphere2.util.GlobalResponse;
import com.example.biliosphere2.util.LoggingFile;
import com.example.biliosphere2.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class PeminjamanService implements IService<ValPeminjamanDTO> {

    @Autowired
    private PeminjamanRepo peminjamanRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> save(ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        try {
            if (peminjamanDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT04001", request);
            }

            Peminjaman peminjaman = convertToPeminjaman(peminjamanDTO);
            peminjaman.setCreatedBy("erlan");
            peminjaman.setCreatedDate(new Date());
            peminjamanRepo.save(peminjaman);
        } catch (Exception e) {
            LoggingFile.logException("PeminjamanService", "save --> Line 52", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT04001", request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        try {
            if (peminjamanDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT04011", request);
            }
            Optional<Peminjaman> peminjamanOptional = peminjamanRepo.findById(id);
            if (!peminjamanOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Peminjaman peminjamanDB = peminjamanOptional.get();
            peminjamanDB.setUpdatedBy("erlan");
            peminjamanDB.setUpdatedDate(new Date());
            peminjamanDB.setStatusPengembalian(peminjamanDTO.getStatusPengembalian());
            peminjamanDB.setTanggalKembali(peminjamanDTO.getTanggalKembali());
            peminjamanRepo.save(peminjamanDB);
        } catch (Exception e) {
            LoggingFile.logException("PeminjamanService", "update --> Line 75", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT04011", request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            Optional<Peminjaman> peminjamanOptional = peminjamanRepo.findById(id);
            if (!peminjamanOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            peminjamanRepo.deleteById(id);
        } catch (Exception e) {
            LoggingFile.logException("PeminjamanService", "delete --> Line 90", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT04021", request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Peminjaman> page = peminjamanRepo.findAll(pageable);
        List<Peminjaman> list = page.getContent();
        List<RespPeminjamanDTO> listDTO = convertToListRespPeminjamanDTO(list);
        if (list.isEmpty()) {
            return GlobalResponse.dataTidakDitemukan(request);
        }
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, "id", "");
        return GlobalResponse.dataResponseList(mapList, request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        RespPeminjamanDTO respPeminjamanDTO;
        try {
            Optional<Peminjaman> peminjamanOptional = peminjamanRepo.findById(id);
            if (!peminjamanOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Peminjaman peminjamanDB = peminjamanOptional.get();
            respPeminjamanDTO = modelMapper.map(peminjamanDB, RespPeminjamanDTO.class);
        } catch (Exception e) {
            LoggingFile.logException("PeminjamanService", "findById --> Line 128", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiakses("FEAUT04041", request);
        }
        return new ResponseHandler().handleResponse("OK", HttpStatus.OK, respPeminjamanDTO, null, request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Peminjaman> page;
        switch (columnName) {
            case "userNama":
                page = peminjamanRepo.findByUserNamaContainsIgnoreCase(pageable, value);
                break;
            case "bukuJudul":
                page = peminjamanRepo.findByBukuJudulContainsIgnoreCase(pageable, value);
                break;
            case "statusPengembalian":
                page = peminjamanRepo.findByStatusPengembalian(pageable, Boolean.parseBoolean(value));
                break;
            default:
                page = peminjamanRepo.findAll(pageable);
                break;
        }
        List<Peminjaman> list = page.getContent();
        if (list.isEmpty()) {
            return GlobalResponse.dataTidakDitemukan(request);
        }
        List<RespPeminjamanDTO> listDTO = convertToListRespPeminjamanDTO(list);
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, columnName, value);
        return GlobalResponse.dataResponseList(mapList, request);
    }

    public List<RespPeminjamanDTO> convertToListRespPeminjamanDTO(List<Peminjaman> peminjamanList) {
        return modelMapper.map(peminjamanList, new TypeToken<List<RespPeminjamanDTO>>() {}.getType());
    }

    public Peminjaman convertToPeminjaman(ValPeminjamanDTO peminjamanDTO) {
        return modelMapper.map(peminjamanDTO, Peminjaman.class);
    }
}
