package com.example.biliosphere2.service;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 5:14 AM
@Last Modified 2/4/2025 5:14 AM
Version 1.0
*/

import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.core.IService;
import com.example.biliosphere2.dto.response.RespBukuDTO;
import com.example.biliosphere2.dto.validasi.ValBukuDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.Buku;
import com.example.biliosphere2.model.Kategori;
import com.example.biliosphere2.repository.BukuRepo;
import com.example.biliosphere2.repository.KategoriRepo;
import com.example.biliosphere2.util.GlobalResponse;
import com.example.biliosphere2.util.LoggingFile;
import com.example.biliosphere2.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BukuService implements IService<ValBukuDTO> {

    @Autowired
    private BukuRepo bukuRepo;

    @Autowired
    private KategoriRepo kategoriRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> save(ValBukuDTO bukuDTO, HttpServletRequest request) {
        try {
            if (bukuDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT03001", request);
            }

            // Check if book with same title already exists
            Page<Buku> existingBuku = bukuRepo.findByJudulContainsIgnoreCase(PageRequest.of(0, 1), bukuDTO.getJudul());
            if (existingBuku.hasContent()) {
                return ResponseEntity.badRequest().body("Buku sudah ada!");
            }

            // Validate Kategori
            Optional<Kategori> kategoriOptional = kategoriRepo.findById(bukuDTO.getKategoriId());
            if (!kategoriOptional.isPresent()) {
                return GlobalResponse.dataTidakValid("FVAUT03002", request);
            }

            Buku buku = convertToBuku(bukuDTO);
            buku.setKategori(kategoriOptional.get());
            buku.setCreatedBy("system");
            buku.setCreatedDate(new Date());

            bukuRepo.save(buku);
        } catch (Exception e) {
            LoggingFile.logException("BukuService", "save --> Line 62", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT03001", request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, ValBukuDTO bukuDTO, HttpServletRequest request) {
        try {
            if (bukuDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT03011", request);
            }
            Optional<Buku> bukuOptional = bukuRepo.findById(id);
            if (!bukuOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }

            // Validate Kategori
            Optional<Kategori> kategoriOptional = kategoriRepo.findById(bukuDTO.getKategoriId());
            if (!kategoriOptional.isPresent()) {
                return GlobalResponse.dataTidakValid("FVAUT03012", request);
            }

            Buku bukuDB = bukuOptional.get();
            bukuDB.setUpdatedBy("erlan");
            bukuDB.setUpdatedDate(new Date());

            // Update specific book fields
            bukuDB.setJudul(bukuDTO.getJudul());
            bukuDB.setPenulis(bukuDTO.getPenulis());
            bukuDB.setPenerbit(bukuDTO.getPenerbit());
            bukuDB.setTahunTerbit(bukuDTO.getTahunTerbit());
            bukuDB.setStok(bukuDTO.getStok());
            bukuDB.setHarga(bukuDTO.getHarga());
            bukuDB.setKategori(kategoriOptional.get());

            bukuRepo.save(bukuDB);
        } catch (Exception e) {
            LoggingFile.logException("BukuService", "update --> Line 86", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT03011", request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try{
            Optional<Buku> bukuOptional = bukuRepo.findById(id);
            if(!bukuOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            bukuRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.logException("BukuService","delete --> Line 102",e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT03021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Buku> page = bukuRepo.findAll(pageable);
        List<Buku> list = page.getContent();
        List<RespBukuDTO> listDTO = convertToListRespBukuDTO(list);
        if (list.isEmpty()) {
            return GlobalResponse.dataTidakDitemukan(request);
        }
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, "id", "");
        return GlobalResponse.dataResponseList(mapList, request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        RespBukuDTO respBukuDTO;
        try {
            Optional<Buku> bukuOptional = bukuRepo.findById(id);
            if (!bukuOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Buku bukuDB = bukuOptional.get();
            respBukuDTO = convertToRespBukuDTO(bukuDB);
        } catch (Exception e) {
            LoggingFile.logException("BukuService", "findById --> Line 162", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiakses("FEAUT03041", request);
        }
        return new ResponseHandler().handleResponse("OK", HttpStatus.OK, respBukuDTO, null, request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Buku> page;
        List<Buku> list;
        switch(columnName){
            case "judul":
                page = bukuRepo.findByJudulContainsIgnoreCase(pageable, value);
                break;
            case "penulis":
                page = bukuRepo.findByPenulisContainsIgnoreCase(pageable, value);
                break;
            case "penerbit":
                page = bukuRepo.findByPenerbitContainsIgnoreCase(pageable, value);
                break;
            case "tahunTerbit":
                try {
                    Integer tahunTerbit = Integer.parseInt(value);
                    page = bukuRepo.findByTahunTerbit(pageable, tahunTerbit);
                } catch (NumberFormatException e) {
                    page = bukuRepo.findAll(pageable);
                }
                break;
            default:
                page = bukuRepo.findAll(pageable);
                break;
        }
        list = page.getContent();
        if(list.isEmpty()){
            return GlobalResponse.dataTidakDitemukan(request);
        }
        List<RespBukuDTO> listDTO = convertToListRespBukuDTO(list);
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, columnName, value);
        return GlobalResponse.dataResponseList(mapList, request);
    }

    // Custom method to convert Buku to RespBukuDTO with category name
    private RespBukuDTO convertToRespBukuDTO(Buku buku) {
        RespBukuDTO respBukuDTO = modelMapper.map(buku, RespBukuDTO.class);
        respBukuDTO.setNamaKategori(buku.getKategori() != null ? buku.getKategori().getNamaKategori() : null);
        return respBukuDTO;
    }

    public List<RespBukuDTO> convertToListRespBukuDTO(List<Buku> bukuList) {
        return bukuList.stream()
                .map(this::convertToRespBukuDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public Buku convertToBuku(ValBukuDTO bukuDTO){
        Buku buku = modelMapper.map(bukuDTO, Buku.class);
        return buku;
    }

}
