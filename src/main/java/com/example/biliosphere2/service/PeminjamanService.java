
package com.example.biliosphere2.service;


import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.core.IService;
import com.example.biliosphere2.dto.response.RespBukuDTO;
import com.example.biliosphere2.dto.response.RespPeminjamanDTO;
import com.example.biliosphere2.dto.response.RespStatusPengembalianDTO;
import com.example.biliosphere2.dto.validasi.ValPeminjamanDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.*;
import com.example.biliosphere2.model.enums.StatusPembayaran;
import com.example.biliosphere2.model.enums.StatusPengembalianEnum;
import com.example.biliosphere2.repository.*;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
    private DendaRepo dendaRepo;
    @Autowired
    private DendaService dendaService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TransformPagination transformPagination;

    public ResponseEntity<Object> save(ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        try {

            if (peminjamanDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT03001", request);
            }
            StatusPembayaran statusPembayaranPeminjaman = StatusPembayaran.BELUM_DIBAYAR; // Default value

            // Cek user & buku
            Optional<User> user = userRepo.findById(peminjamanDTO.getIdUser());
            Optional<Buku> buku = bukuRepo.findById(peminjamanDTO.getIdBuku());
            if (user.isEmpty() || buku.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }

            // Validasi status pengembalian dari Enum
            StatusPengembalianEnum statusEnum;
            try {
                statusEnum = StatusPengembalianEnum.valueOf(peminjamanDTO.getStatusPengembalian().toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("❌ Status pengembalian tidak valid!");
            }

            // Simpan peminjaman baru
            Peminjaman peminjaman = new Peminjaman();
            peminjaman.setUser(user.get());
            peminjaman.setBuku(buku.get());
            peminjaman.setStatusPengembalian(statusEnum);
            peminjaman.setStatusPembayaran(statusPembayaranPeminjaman);
            peminjaman.setCreatedBy("erlan");
            peminjaman.setTanggalPinjam(peminjamanDTO.getTanggalPinjam());
            peminjaman.setTanggalKembali(peminjamanDTO.getTanggalKembali());

            // Kurangi stok buku
            Buku bukuDipinjam = buku.get();
            bukuDipinjam.setStok(bukuDipinjam.getStok() - 1);
            bukuRepo.save(bukuDipinjam);

            peminjamanRepo.save(peminjaman);
            return GlobalResponse.dataBerhasilDisimpan(request);

        } catch (Exception e) {
            LoggingFile.logException("PeminjamanService", "save", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT03001", request);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        try {
            if (peminjamanDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT03011", request);
            }

            // ✅ Cek apakah peminjaman ada di database
            Optional<Peminjaman> peminjamanOptional = peminjamanRepo.findById(id);
            if (peminjamanOptional.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Peminjaman peminjaman = peminjamanOptional.get();
            StatusPengembalianEnum statusLama = peminjaman.getStatusPengembalian();

            StatusPembayaran statusPembayaranPeminjaman;
            if (peminjamanDTO.getStatusPembayaran() != null) {
                try {
                    statusPembayaranPeminjaman = peminjamanDTO.getStatusPembayaran();
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().body("❌ Status pembayaran tidak valid!");
                }
            } else {
                statusPembayaranPeminjaman = peminjaman.getStatusPembayaran();
            }

            // ✅ Validasi data user & buku
            Optional<User> user = userRepo.findById(peminjamanDTO.getIdUser());
            Optional<Buku> buku = bukuRepo.findById(peminjamanDTO.getIdBuku());
            if (user.isEmpty() || buku.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }

            // ✅ Validasi status pengembalian dari Enum
            if (peminjamanDTO.getStatusPengembalian() == null || peminjamanDTO.getStatusPengembalian().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Status pengembalian tidak boleh kosong!");
            }

            // Hardcode user untuk sementara
            String currentUser = "SYSTEM";

            // ✅ Validasi tanggal kembali
            LocalDate tanggalKembali = peminjamanDTO.getTanggalKembali();
            if (tanggalKembali != null && tanggalKembali.isBefore(peminjaman.getTanggalPinjam())) {
                return ResponseEntity.badRequest().body("❌ Tanggal kembali tidak boleh sebelum tanggal pinjam");
            }

            // ✅ Hitung keterlambatan
            LocalDate batasPengembalian = peminjaman.getTanggalPinjam().plusDays(7);
            long daysLate = (tanggalKembali != null && tanggalKembali.isAfter(batasPengembalian)) ?
                    ChronoUnit.DAYS.between(batasPengembalian, tanggalKembali) : 0;

            // ✅ Tentukan status berdasarkan keterlambatan
            String requestedStatus = peminjamanDTO.getStatusPengembalian().toUpperCase();
            StatusPengembalianEnum statusBaru;

// 🔹 Set status yang hanya boleh digunakan jika benar-benar terlambat
            Set<StatusPengembalianEnum> statusTerlambat = Set.of(
                    StatusPengembalianEnum.TERLAMBAT,
                    StatusPengembalianEnum.RUSAK_RINGAN_TERLAMBAT,
                    StatusPengembalianEnum.RUSAK_BERAT_TERLAMBAT,
                    StatusPengembalianEnum.HILANG_TERLAMBAT
            );

            try {
                statusBaru = StatusPengembalianEnum.valueOf(requestedStatus);

                // 🔹 Cek apakah status yang dimasukkan adalah status terlambat
                if (statusTerlambat.contains(statusBaru) && daysLate <= 0) {
                    return ResponseEntity.badRequest().body("❌ Status pengembalian '" + statusBaru + "' hanya boleh digunakan jika pengembalian terlambat.");
                }
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("❌ Status pengembalian tidak valid!");
            }

            // ✅ Validasi tanggal kembali wajib untuk status tertentu
            if ((statusBaru == StatusPengembalianEnum.DIKEMBALIKAN ||
                    statusBaru == StatusPengembalianEnum.TERLAMBAT ||
                    statusBaru.name().contains("_TERLAMBAT")) && tanggalKembali == null) {
                return ResponseEntity.badRequest().body("❌ Tanggal kembali wajib diisi untuk status ini");
            }

            // ✅ Update data peminjaman
            peminjaman.setUser(user.get());
            peminjaman.setBuku(buku.get());
            peminjaman.setStatusPengembalian(statusBaru);
            peminjaman.setTanggalKembali(tanggalKembali);
            peminjaman.setStatusPembayaran(statusPembayaranPeminjaman);
            peminjaman.setUpdatedBy(currentUser);
            peminjaman.setUpdatedDate(LocalDate.now());

            // ✅ Proses perubahan stok buku
            // ✅ Proses perubahan stok buku
            Buku bukuUpdate = buku.get();
            if (!statusBaru.equals(statusLama)) {

                // 🔹 Set status yang menambah stok
                Set<StatusPengembalianEnum> statusMenambahStok = Set.of(
                        StatusPengembalianEnum.DIKEMBALIKAN,
                        StatusPengembalianEnum.TERLAMBAT,
                        StatusPengembalianEnum.RUSAK_RINGAN,
                        StatusPengembalianEnum.RUSAK_RINGAN_TERLAMBAT,
                        StatusPengembalianEnum.DIGANTI
                );

                // 🔹 Set status yang mengurangi stok
                Set<StatusPengembalianEnum> statusMengurangiStok = Set.of(
                        StatusPengembalianEnum.HILANG,
                        StatusPengembalianEnum.HILANG_TERLAMBAT,
                        StatusPengembalianEnum.RUSAK_BERAT_TERLAMBAT,
                        StatusPengembalianEnum.RUSAK_BERAT
                );

                // 🔹 Hanya perlu menghandle status baru
                if (statusMenambahStok.contains(statusBaru)) {
                    bukuUpdate.setStok(bukuUpdate.getStok() + 1);
                } else if (statusMengurangiStok.contains(statusBaru)) {
                    bukuUpdate.setStok(bukuUpdate.getStok() - 1);
                }

                // ✅ Simpan perubahan stok hanya jika benar-benar ada perubahan
                bukuRepo.save(bukuUpdate);
            }


            // ✅ Proses denda
            BigDecimal jumlahDenda = dendaService.hitungDenda(peminjaman);
            Optional<Denda> existingDenda = dendaRepo.findByPeminjaman(peminjaman);
            if (statusPembayaranPeminjaman == null) {
                statusPembayaranPeminjaman = StatusPembayaran.BELUM_DIBAYAR;  // Default jika null
            }

            // Jika status pembayaran sudah diatur dari request, gunakan nilai itu
            if (statusPembayaranPeminjaman == null) {
                if (statusBaru == StatusPengembalianEnum.DIKEMBALIKAN && jumlahDenda.compareTo(BigDecimal.ZERO) == 0) {
                    statusPembayaranPeminjaman = StatusPembayaran.LUNAS;
                } else {
                    statusPembayaranPeminjaman = StatusPembayaran.BELUM_DIBAYAR;
                }
            }

// ✅ Simpan status pembayaran yang diperbarui
            peminjaman.setStatusPembayaran(statusPembayaranPeminjaman);


            peminjamanRepo.save(peminjaman);
            if (existingDenda.isPresent()) {
                Denda denda = existingDenda.get();
                denda.setJumlahDenda(jumlahDenda);
                denda.setTanggalDenda(LocalDate.now());
                denda.setUpdatedBy(currentUser);
                denda.setUpdatedDate(LocalDate.now());
                denda.setStatusPembayaran(peminjaman.getStatusPembayaran());
                dendaRepo.save(denda);
            } else {
                Denda dendaBaru = new Denda();
                dendaBaru.setPeminjaman(peminjaman);
                dendaBaru.setJumlahDenda(jumlahDenda);
                dendaBaru.setTanggalDenda(LocalDate.now());
                dendaBaru.setCreatedBy(currentUser);
                dendaBaru.setCreatedDate(LocalDate.now());
                dendaBaru.setUpdatedBy(currentUser);
                dendaBaru.setUpdatedDate(LocalDate.now());
                dendaBaru.setStatusPembayaran(peminjaman.getStatusPembayaran());
                dendaRepo.save(dendaBaru);
            }

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
            LoggingFile.logException("PeminjamanService", "findById", e, OtherConfig.getEnableLogFile());
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

            switch (columnName) {
                case "userId":
                    try {
                        Long userId = Long.parseLong(value);
                        page = peminjamanRepo.findByUser_Id(pageable, userId);
                    } catch (NumberFormatException e) {
                        return GlobalResponse.dataTidakValid("FVAUT03061", request);
                    }
                    break;

                case "bukuId":
                    try {
                        Long bukuId = Long.parseLong(value);
                        page = peminjamanRepo.findByBuku_Id(pageable, bukuId);
                    } catch (NumberFormatException e) {
                        return GlobalResponse.dataTidakValid("FVAUT03061", request);
                    }
                    break;

                case "statusPengembalian":
                    try {
                        StatusPengembalianEnum statusEnum = StatusPengembalianEnum.valueOf(value.toUpperCase());
                        page = peminjamanRepo.findByStatusPengembalian(pageable, statusEnum);
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.badRequest().body("❌ Status pengembalian tidak valid!");
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
        RespPeminjamanDTO respPeminjamanDTO = modelMapper.map(peminjaman, RespPeminjamanDTO.class);

        // ✅ Set status pengembalian langsung String
        if (peminjaman.getStatusPengembalian() != null) {
            respPeminjamanDTO.setStatusPengembalian(peminjaman.getStatusPengembalian().name());
        }

        // ✅ Set data buku
        if (peminjaman.getBuku() != null) {
            respPeminjamanDTO.setBuku(new RespBukuDTO(
                    peminjaman.getBuku().getId(),
                    peminjaman.getBuku().getJudul()
            ));
        }

        // ✅ Set jumlah denda jika ada
        Optional<Denda> denda = dendaRepo.findByPeminjaman(peminjaman);
        respPeminjamanDTO.setJumlahDenda(denda.map(Denda::getJumlahDenda).orElse(BigDecimal.ZERO));

        return respPeminjamanDTO;
    }



    private List<RespPeminjamanDTO> convertToListRespPeminjamanDTO(List<Peminjaman> peminjamanList) {
        return peminjamanList.stream()
                .map(this::convertToRespPeminjamanDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    private Peminjaman convertToPeminjaman(ValPeminjamanDTO peminjamanDTO) {
        return modelMapper.map(peminjamanDTO, Peminjaman.class);
    }
}
