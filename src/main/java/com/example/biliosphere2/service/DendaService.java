package com.example.biliosphere2.service;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/4/2025 2:30 AM
@Last Modified 2/4/2025 2:30 AM
Version 1.0
*/

import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.core.IService;
import com.example.biliosphere2.dto.response.RespDendaDTO;
import com.example.biliosphere2.dto.validasi.ValDendaDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.Denda;
import com.example.biliosphere2.repository.DendaRepo;
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
public class DendaService implements IService<ValDendaDTO> {

    @Autowired
    private DendaRepo dendaRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> save(ValDendaDTO dendaDTO, HttpServletRequest request) {
        try {
            if (dendaDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT03001", request);
            }

            Denda denda = convertToDenda(dendaDTO);
            denda.setCreatedBy("erlan");
            denda.setCreatedDate(new Date());
            dendaRepo.save(denda);
        } catch (Exception e) {
            LoggingFile.logException("DendaService", "save --> Line 52", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT03001", request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, ValDendaDTO dendaDTO, HttpServletRequest request) {
        try {
            if (dendaDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT03011", request);
            }
            Optional<Denda> dendaOptional = dendaRepo.findById(id);
            if (!dendaOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Denda dendaDB = dendaOptional.get();
            dendaDB.setUpdatedBy("erlan");
            dendaDB.setUpdatedDate(new Date());
            dendaDB.setJumlahDenda(dendaDTO.getJumlahDenda());
            dendaDB.setStatusPembayaran(dendaDTO.getStatusPembayaran());
            dendaDB.setTanggalDenda(dendaDTO.getTanggalDenda());
            dendaRepo.save(dendaDB);
        } catch (Exception e) {
            LoggingFile.logException("DendaService", "update --> Line 75", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT03011", request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            Optional<Denda> dendaOptional = dendaRepo.findById(id);
            if (!dendaOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            dendaRepo.deleteById(id);
        } catch (Exception e) {
            LoggingFile.logException("DendaService", "delete --> Line 90", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT03021", request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<Denda> page = dendaRepo.findAll(pageable);
        List<Denda> list = page.getContent();
        List<RespDendaDTO> listDTO = convertToListRespDendaDTO(list);
        if (list.isEmpty()) {
            return GlobalResponse.dataTidakDitemukan(request);
        }
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, "id", "");
        return GlobalResponse.dataResponseList(mapList, request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        RespDendaDTO respDendaDTO;
        try {
            Optional<Denda> dendaOptional = dendaRepo.findById(id);
            if (!dendaOptional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Denda dendaDB = dendaOptional.get();
            respDendaDTO = modelMapper.map(dendaDB, RespDendaDTO.class);
        } catch (Exception e) {
            LoggingFile.logException("DendaService", "findById --> Line 128", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiakses("FEAUT03041", request);
        }
        return new ResponseHandler().handleResponse("OK", HttpStatus.OK, respDendaDTO, null, request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        Page<Denda> page;
        switch (columnName) {
            case "peminjamanId":
                page = dendaRepo.findByPeminjamanId(Long.parseLong(value), pageable);
                break;
            case "statusPembayaran":
                page = dendaRepo.findByStatusPembayaran(Boolean.parseBoolean(value), pageable);
                break;
            default:
                page = dendaRepo.findAll(pageable);
                break;
        }
        List<Denda> list = page.getContent();
        if (list.isEmpty()) {
            return GlobalResponse.dataTidakDitemukan(request);
        }
        List<RespDendaDTO> listDTO = convertToListRespDendaDTO(list);
        Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, columnName, value);
        return GlobalResponse.dataResponseList(mapList, request);
    }

    public List<RespDendaDTO> convertToListRespDendaDTO(List<Denda> dendaList) {
        return modelMapper.map(dendaList, new TypeToken<List<RespDendaDTO>>() {}.getType());
    }

    public Denda convertToDenda(ValDendaDTO dendaDTO) {
        return modelMapper.map(dendaDTO, Denda.class);
    }
}
