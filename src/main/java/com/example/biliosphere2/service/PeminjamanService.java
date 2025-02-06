package com.example.biliosphere2.service;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 2/5/2025 1:11 AM
@Last Modified 2/5/2025 1:11 AM
Version 1.0
*/

import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.core.IService;
import com.example.biliosphere2.dto.response.RespPeminjamanDTO;
import com.example.biliosphere2.dto.validasi.ValBukuDTO;
import com.example.biliosphere2.dto.validasi.ValPeminjamanDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.Buku;
import com.example.biliosphere2.model.User;
import com.example.biliosphere2.model.Peminjaman;
import com.example.biliosphere2.model.StatusPengembalian;
import com.example.biliosphere2.repository.PeminjamanRepo;
import com.example.biliosphere2.repository.UserRepo;
import com.example.biliosphere2.repository.BukuRepo;
import com.example.biliosphere2.repository.StatusPengembalianRepo;
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

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class PeminjamanService implements IService<ValPeminjamanDTO> {

    @Autowired
    private PeminjamanRepo peminjamanRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BukuRepo bukuRepo;
    @Autowired
    private StatusPengembalianRepo statusPengembalianRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> save(ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        try {
            // Validate if peminjamanDTO is null
            if (peminjamanDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT03001", request);
            }
            // Validate if userId is valid
            Optional<User> user = userRepo.findById(peminjamanDTO.getUserId());
            if (!user.isPresent()) {
                return GlobalResponse.dataTidakValid("FVAUT03002", request); // User not found
            }
            // Validate if bukuId is valid
            Optional<Buku> buku = bukuRepo.findById(peminjamanDTO.getBukuId());
            if (!buku.isPresent()) {
                return GlobalResponse.dataTidakValid("FVAUT03003", request); // Buku not found
            }
            // Check if the peminjaman already exists
            Page<Peminjaman> existingPeminjaman = peminjamanRepo.findByUser_IdAndBuku_Id(PageRequest.of(0, 1), peminjamanDTO.getUserId(), peminjamanDTO.getBukuId());
            if (existingPeminjaman.hasContent()) {
                return ResponseEntity.badRequest().body("Peminjaman untuk buku ini sudah ada!");
            }
            // Validate if statusPengembalianId is valid
            Optional<StatusPengembalian> status = statusPengembalianRepo.findById(peminjamanDTO.getStatusPengembalianId() != null ? peminjamanDTO.getStatusPengembalianId() : 1L);
            if (!status.isPresent()) {
                return GlobalResponse.dataTidakValid("FVAUT03004", request); // Status pengembalian not found
            }
            // Proceed with creating the peminjaman if all data is valid
            Peminjaman peminjaman = convertToPeminjaman(peminjamanDTO);
            peminjaman.setUser(user.get());
            peminjaman.setBuku(buku.get());
            peminjaman.setStatusPengembalian(status.get());
            peminjaman.setTanggalPinjam(peminjamanDTO.getTanggalPinjam());
            peminjaman.setTanggalKembali(peminjamanDTO.getTanggalKembali());
            peminjaman.setCreatedBy("erlan"); // Set the creator to system or logged-in user
            peminjaman.setCreatedDate(LocalDate.now()); // Set current date

            // Save the peminjaman entity
            peminjamanRepo.save(peminjaman);
            return GlobalResponse.dataBerhasilDisimpan(request); // Return success response

        } catch (Exception e) {
            // Log exception for debugging
            LoggingFile.logException("PeminjamanService", "save", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT03001", request); // Return failure response
        }
    }


    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        try {
            if (peminjamanDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT03011", request);
            }

            Optional<Peminjaman> peminjamanOptional = peminjamanRepo.findById(id);
            if (peminjamanOptional.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }

            var user = userRepo.findById(peminjamanDTO.getUserId());
            var buku = bukuRepo.findById(peminjamanDTO.getBukuId());
            var status = statusPengembalianRepo.findById(peminjamanDTO.getStatusPengembalianId());

            if (user.isEmpty() || buku.isEmpty() || status.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }

            Peminjaman peminjaman = peminjamanOptional.get();
            peminjaman.setUser(user.get());
            peminjaman.setBuku(buku.get());
            peminjaman.setStatusPengembalian(status.get());
            peminjaman.setTanggalKembali(peminjamanDTO.getTanggalKembali());
            peminjaman.setUpdatedBy("system");
            peminjaman.setUpdatedDate(LocalDate.EPOCH);

            peminjamanRepo.save(peminjaman);
            return GlobalResponse.dataBerhasilDiubah(request);
        } catch (Exception e) {
            LoggingFile.logException("PeminjamanService", "update", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiubah("FEAUT03011", request);
        }
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        try {
            if (!peminjamanRepo.existsById(id)) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            peminjamanRepo.deleteById(id);
            return GlobalResponse.dataBerhasilDihapus(request);
        } catch (Exception e) {
            LoggingFile.logException("PeminjamanService", "delete", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDihapus("FEAUT03021", request);
        }
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
            respPeminjamanDTO = convertToRespPeminjamanDTO(peminjamanDB);
        } catch (Exception e) {
            LoggingFile.logException("PeminjamanService", "findById --> Line 162", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiakses("FEAUT03041", request);
        }
        return new ResponseHandler().handleResponse("OK", HttpStatus.OK, respPeminjamanDTO, null, request);
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
    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value, HttpServletRequest request) {
        try {
            Page<Peminjaman> page;
            Long parsedValue = null;

            try {
                if (value != null && !value.isEmpty()) {
                    parsedValue = Long.parseLong(value);
                }
            } catch (NumberFormatException e) {
                return GlobalResponse.dataTidakValid("FVAUT03061", request);
            }

            switch (columnName) {
                case "userId":
                    if (parsedValue != null) {
                        page = peminjamanRepo.findByUser_Id(pageable, parsedValue);
                    } else {
                        return GlobalResponse.dataTidakValid("FVAUT03061", request);
                    }
                    break;
                case "bukuId":
                    if (parsedValue != null) {
                        page = peminjamanRepo.findByBuku_Id(pageable, parsedValue);
                    } else {
                        return GlobalResponse.dataTidakValid("FVAUT03061", request);
                    }
                    break;
                case "statusPengembalianId":
                    if (parsedValue != null) {
                        page = peminjamanRepo.findByStatusPengembalian_Id(pageable, parsedValue);
                    } else {
                        return GlobalResponse.dataTidakValid("FVAUT03061", request);
                    }
                    break;
                default:
                    page = peminjamanRepo.findAll(pageable);
            }

            List<Peminjaman> list = page.getContent();
            if (list.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }

            List<RespPeminjamanDTO> listDTO = convertToListDTO(list);
            Map<String, Object> mapList = transformPagination.transformPagination(listDTO, page, columnName, value);
            return GlobalResponse.dataResponseList(mapList, request);
        } catch (Exception e) {
            LoggingFile.logException("PeminjamanService", "findByParam", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDiakses("FEAUT03051", request);
        }
    }

    private List<RespPeminjamanDTO> convertToListDTO(List<Peminjaman> peminjamanList) {
        return modelMapper.map(peminjamanList, new TypeToken<List<RespPeminjamanDTO>>() {}.getType());
    }

    private RespPeminjamanDTO convertToRespPeminjamanDTO(Peminjaman peminjaman) {
        // Mapping objek Peminjaman ke RespPeminjamanDTO
        RespPeminjamanDTO respPeminjamanDTO = modelMapper.map(peminjaman, RespPeminjamanDTO.class);

        // Menetapkan statusPengembalianNama sesuai kategori dari peminjaman (jika ada)
        respPeminjamanDTO.setStatusPengembalianNama(peminjaman.getStatusPengembalian() != null ? peminjaman.getStatusPengembalian().getStatus() : null);
        // Jika ada buku, menetapkan judul buku
        if (peminjaman.getBuku() != null) {
            respPeminjamanDTO.setJudulBuku(peminjaman.getBuku().getJudul());
        }
        // Mengatur dendaId jika ada
//        if (peminjaman.getDenda() != null) {
//            respPeminjamanDTO.setDendaId(peminjaman.getDenda().getId());
//        }
        return respPeminjamanDTO;
    }
    public List<RespPeminjamanDTO> convertToListRespPeminjamanDTO(List<Peminjaman> peminjamanList) {
        return peminjamanList.stream()
                .map(this::convertToRespPeminjamanDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    public Peminjaman convertToPeminjaman(ValPeminjamanDTO peminjamanDTO){
        Peminjaman peminjaman = modelMapper.map(peminjamanDTO, Peminjaman.class);
        return peminjaman;
    }

}
