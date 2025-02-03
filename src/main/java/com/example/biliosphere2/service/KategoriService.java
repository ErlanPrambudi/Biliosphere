package com.example.biliosphere2.service;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 1:11 AM
@Last Modified 2/4/2025 1:11 AM
Version 1.0
*/


import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.core.IService;
import com.example.biliosphere2.dto.response.RespKategoriDTO;
import com.example.biliosphere2.dto.response.TableKategoriDTO;
import com.example.biliosphere2.dto.response.TableMenuDTO;
import com.example.biliosphere2.dto.validasi.ValKategoriDTO;
import com.example.biliosphere2.dto.validasi.ValMenuDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.Kategori;
import com.example.biliosphere2.model.Menu;
import com.example.biliosphere2.repository.KategoriRepo;
import com.example.biliosphere2.util.GlobalResponse;
import com.example.biliosphere2.util.LoggingFile;
import com.example.biliosphere2.util.TransformPagination;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
public class KategoriService implements IService<ValKategoriDTO> {

    @Autowired
    private KategoriRepo kategoriRepo;

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private TransformPagination transformPagination;


    @Override
    public ResponseEntity<Object> save(ValKategoriDTO kategoriDTO, HttpServletRequest request) {
        try {
            if (kategoriDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT02001", request);
            }

            Page<Kategori> existingKategori = kategoriRepo.findByNamaKategoriContainsIgnoreCase(PageRequest.of(0, 1), kategoriDTO.getNamaKategori());
            if (existingKategori.hasContent()) {
                return ResponseEntity.badRequest().body("Kategori sudah ada!");
            }

            Kategori kategori = convertToKategori(kategoriDTO);
            kategori.setCreatedBy("erlan");
            kategori.setCreatedDate(new Date());
            kategoriRepo.save(kategori);
        } catch (Exception e) {
            LoggingFile.logException("KategoriService", "save --> Line 62", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT02001", request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, ValKategoriDTO kategoriDTO, HttpServletRequest request) {
        try {
            if (kategoriDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT02011", request);
            }
            Optional<Kategori> kategoriOptional = kategoriRepo.findById(id);
            if (!kategoriOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Kategori kategoriDB = kategoriOptional.get();
            kategoriDB.setUpdatedBy("Erlan");
            kategoriDB.setUpdatedDate(new Date());
            kategoriDB.setNamaKategori(kategoriDTO.getNamaKategori());
            kategoriRepo.save(kategoriDB);
        } catch (Exception e) {
            // Log exception jika terjadi kesalahan
            LoggingFile.logException("KategoriService", "update --> Line 86", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT02011", request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try{
            Optional<Kategori> menuOptional = kategoriRepo.findById(id);
            if(!menuOptional.isPresent()){
                return GlobalResponse.dataTidakDitemukan(request);
            }
            kategoriRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.logException("KategoriService","delete --> Line 102",e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT02021",request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Kategori> page = null;
        List<Kategori> list = null;
        page = kategoriRepo.findAll(pageable);// Mengambil data kategori dengan pagination
        list = page.getContent();
        List<RespKategoriDTO> listDTO = convertToListRespKategoriDTO(list);
        if (list.isEmpty()) {
            return GlobalResponse.dataTidakDitemukan(request);
        }
        // Transformasi pagination jika diperlukan, misalnya menambahkan informasi pagination
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, "id", "");
        return GlobalResponse.dataResponseList(mapList, request);
    }


    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        RespKategoriDTO respKategoriDTO;
        try {
            Optional<Kategori> kategoriOptional = kategoriRepo.findById(id);
            if (!kategoriOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Kategori kategoriDB = kategoriOptional.get();
            respKategoriDTO = modelMapper.map(kategoriDB, RespKategoriDTO.class);
        } catch (Exception e) {
            // Logging exception jika terjadi error
            LoggingFile.logException("KategoriService", "findById --> Line 162", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiakses("FEAUT02041", request);
        }
        return new ResponseHandler().handleResponse("OK", HttpStatus.OK, respKategoriDTO, null, request);
    }


    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Kategori> page = null;
        List<Kategori> list = null;
        switch(columnName){
            case "nama":
                page = kategoriRepo.findByNamaKategoriContainsIgnoreCase(pageable, value);
                break;
            default :
                page = kategoriRepo.findAll(pageable);
                break;
        }
        list = page.getContent();
        if(list.isEmpty()){
            return GlobalResponse.dataTidakDitemukan(request);
        }
        List<RespKategoriDTO> listDTO = convertToListRespKategoriDTO(list);
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, columnName, value);
        return GlobalResponse.dataResponseList(mapList, request);
    }
    public List<RespKategoriDTO> convertToListRespKategoriDTO(List<Kategori> kategoriList) {
        return modelMapper.map(kategoriList, new TypeToken<List<RespKategoriDTO>>(){}.getType());
    }

    public Kategori convertToKategori(ValKategoriDTO kategoriDTO){
        return modelMapper.map(kategoriDTO,Kategori.class);
    }


    public List<TableKategoriDTO> convertToTableKategoriDTO(List<Kategori> kategoriList) {
        List<TableKategoriDTO> list = new ArrayList<>();
        TableKategoriDTO tableKategoriDTO;
        for (Kategori kategori : kategoriList) {
            tableKategoriDTO = new TableKategoriDTO();
            tableKategoriDTO.setId(kategori.getId());
            tableKategoriDTO.setNamaKategori(kategori.getNamaKategori());
            list.add(tableKategoriDTO);
        }
        return list;
    }
}