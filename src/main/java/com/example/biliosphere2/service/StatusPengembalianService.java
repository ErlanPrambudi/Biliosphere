package com.example.biliosphere2.service;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/5/2025 3:57 AM
@Last Modified 2/5/2025 3:57 AM
Version 1.0
*/

import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.dto.response.RespStatusPengembalianDTO;
import com.example.biliosphere2.dto.validasi.ValStatusPengembalianDTO;
import com.example.biliosphere2.model.StatusPengembalian;
import com.example.biliosphere2.repository.StatusPengembalianRepo;
import com.example.biliosphere2.util.GlobalResponse;
import com.example.biliosphere2.util.LoggingFile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StatusPengembalianService {

    @Autowired
    private StatusPengembalianRepo statusPengembalianRepo;

    @Autowired
    private ModelMapper modelMapper;

    public ResponseEntity<Object> save(ValStatusPengembalianDTO dto, HttpServletRequest request) {
        try {
            if (dto == null) {
                return GlobalResponse.dataTidakValid("FVAUT02001", request);
            }
            Optional<StatusPengembalian> existing = statusPengembalianRepo.findByStatus(dto.getStatus());
            if (existing.isPresent()) {
                return ResponseEntity.badRequest().body("Status sudah ada");
            }
//            // Menggunakan nilai status yang ada di DTO atau default "Dipinjam"
//            String status = dto.getStatus() != null ? dto.getStatus() : "Dipinjam";
            // Map DTO ke entitas dan set properti lainnya
            StatusPengembalian statusPengembalian = modelMapper.map(dto, StatusPengembalian.class);
            //statusPengembalian.setStatus(status);
            statusPengembalian.setCreatedDate(new Date());
            statusPengembalian.setCreatedBy("erlan");
            // Simpan entitas status pengembalian
            statusPengembalianRepo.save(statusPengembalian);
        } catch (Exception e) {
            LoggingFile.logException("StatusPengembalianService", "save --> Line 58", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT02001", request);
        }
        return GlobalResponse.dataBerhasilDisimpan(request);
    }


    public ResponseEntity<Object> update(Long id, ValStatusPengembalianDTO dto, HttpServletRequest request) {
        try {
            Optional<StatusPengembalian> optional = statusPengembalianRepo.findById(id);
            if (!optional.isPresent()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            StatusPengembalian statusPengembalian = optional.get();
            statusPengembalian.setStatus(dto.getStatus());
            statusPengembalian.setUpdatedBy(dto.getCreatedBy());
            statusPengembalian.setUpdatedDate(new Date());
            statusPengembalianRepo.save(statusPengembalian);
        } catch (Exception e) {
            LoggingFile.logException("StatusPengembalianService", "update --> Line 80", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT02011", request);
        }
        return GlobalResponse.dataBerhasilDiubah(request);
    }

    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (!statusPengembalianRepo.existsById(id)) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            statusPengembalianRepo.deleteById(id);
        } catch (Exception e) {
            LoggingFile.logException("StatusPengembalianService", "delete --> Line 94", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT02021", request);
        }
        return GlobalResponse.dataBerhasilDihapus(request);
    }

    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<StatusPengembalian> page = statusPengembalianRepo.findAll(pageable);
        List<RespStatusPengembalianDTO> listDTO = page.getContent().stream()
                .map(item -> modelMapper.map(item, RespStatusPengembalianDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listDTO);
    }

    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<StatusPengembalian> optional = statusPengembalianRepo.findById(id);
        if (!optional.isPresent()) {
            return GlobalResponse.dataTidakDitemukan(request);
        }
        RespStatusPengembalianDTO dto = modelMapper.map(optional.get(), RespStatusPengembalianDTO.class);
        return ResponseEntity.ok(dto);
    }

    public ResponseEntity<Object> findByParam(Pageable pageable, String value, HttpServletRequest request) {
        Page<StatusPengembalian> page = statusPengembalianRepo.findByStatusContainsIgnoreCase(pageable, value);
        List<RespStatusPengembalianDTO> listDTO = page.getContent().stream()
                .map(item -> modelMapper.map(item, RespStatusPengembalianDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listDTO);
    }
}

