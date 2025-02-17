
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
import com.example.biliosphere2.model.enums.StatusPeminjamanEnum;
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

            // Cek user & buku
            Optional<User> user = userRepo.findById(peminjamanDTO.getIdUser());
            Optional<Buku> buku = bukuRepo.findById(peminjamanDTO.getIdBuku());
            if (user.isEmpty() || buku.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }

            // Cek apakah user masih meminjam buku yang sama
            if (peminjamanRepo.existsByUser_IdAndBuku_IdAndStatusPengembalianNotInAndTanggalKembaliIsNull(
                    peminjamanDTO.getIdUser(),
                    peminjamanDTO.getIdBuku(),
                    Arrays.asList(StatusPengembalianEnum.DIKEMBALIKAN))) {
                return ResponseEntity.badRequest().body("❌ User masih meminjam buku yang sama!");
            }

            // Cek jumlah buku yang sedang dipinjam user
            long activeBooksCount = peminjamanRepo.countByUser_IdAndTanggalKembaliIsNull(peminjamanDTO.getIdUser());
            if (activeBooksCount >= 2) {
                return ResponseEntity.badRequest().body("❌ User sudah meminjam maksimal 2 buku!");
            }

            // Cek stok buku
            Buku bukuDipinjam = buku.get();
            if (bukuDipinjam.getStok() <= 0) {
                return ResponseEntity.badRequest().body("❌ Stok buku tidak tersedia!");
            }

            // Validasi status pengembalian dari Enum (Kalau diisi)
            StatusPengembalianEnum statusEnum = null; // Default null
            if (peminjamanDTO.getStatusPengembalian() != null) {
                try {
                    statusEnum = StatusPengembalianEnum.valueOf(peminjamanDTO.getStatusPengembalian().toUpperCase());
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().body("❌ Status pengembalian tidak valid!");
                }
            }

            // 🔥 Validasi status peminjaman dari Enum (Kalau diisi)
            StatusPeminjamanEnum statusPeminjamanEnum = StatusPeminjamanEnum.DIAJUKAN; // Default "DIAJUKAN"
            if (peminjamanDTO.getStatusPeminjaman() != null) {
                try {
                    statusPeminjamanEnum = StatusPeminjamanEnum.valueOf(peminjamanDTO.getStatusPeminjaman().toUpperCase());
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().body("❌ Status peminjaman tidak valid!");
                }
            }

            // Simpan peminjaman baru
            Peminjaman peminjaman = new Peminjaman();
            peminjaman.setUser(user.get());
            peminjaman.setBuku(bukuDipinjam);
            peminjaman.setStatusPengembalian(statusEnum); // Bisa null
            peminjaman.setStatusPeminjaman(statusPeminjamanEnum); // Bisa "DIAJUKAN", "DISETUJUI", atau "DITOLAK"
            peminjaman.setCreatedBy("erlan");
            peminjaman.setTanggalPinjam(peminjamanDTO.getTanggalPinjam());
            peminjaman.setTanggalKembali(peminjamanDTO.getTanggalPinjam().plusDays(7));

            peminjamanRepo.save(peminjaman);
            return GlobalResponse.dataBerhasilDisimpan(request);

        } catch (Exception e) {
            LoggingFile.logException("PeminjamanService", "save", e, OtherConfig.getEnableLogFile());
            return GlobalResponse.dataGagalDisimpan("FEAUT03001", request);
        }
    }




    @Transactional
    public ResponseEntity<Object> update(Long id, ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        try {
            // ✅ Validasi input awal
            if (peminjamanDTO == null || peminjamanDTO.getStatusPeminjaman() == null) {
                return GlobalResponse.dataTidakValid("FVAUT03011", request);
            }

            // ✅ Cek apakah peminjaman ada di database
            Optional<Peminjaman> peminjamanOptional = peminjamanRepo.findById(id);
            if (peminjamanOptional.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Peminjaman peminjaman = peminjamanOptional.get();
            Optional<Buku> bukuOptional = bukuRepo.findById(peminjamanDTO.getIdBuku());
            if (bukuOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Buku tidak ditemukan!");
            }
            Buku bukuUpdate = bukuOptional.get();

            // ✅ Konversi status dari String ke Enum dengan validasi
            StatusPeminjamanEnum statusPeminjamanBaru;
            try {
                statusPeminjamanBaru = StatusPeminjamanEnum.valueOf(peminjamanDTO.getStatusPeminjaman().toUpperCase());
            } catch (IllegalArgumentException e) {
                return GlobalResponse.dataTidakValid("INVALID_STATUS", request);
            }

            // ✅ Jika status saat ini DITOLAK, tidak boleh diubah lagi
            if (peminjaman.getStatusPeminjaman() == StatusPeminjamanEnum.DITOLAK) {
                return GlobalResponse.dataTidakValid("INVALID_STATUS_REJECTED", request);
            }

            // ✅ Jika status peminjaman masih DIAJUKAN
            if (peminjaman.getStatusPeminjaman() == StatusPeminjamanEnum.DIAJUKAN || peminjaman.getStatusPeminjaman() == StatusPeminjamanEnum.DISETUJUI) {
                if (statusPeminjamanBaru == StatusPeminjamanEnum.DITOLAK) {
                    peminjaman.setStatusPeminjaman(StatusPeminjamanEnum.DITOLAK);
                    peminjaman.setUpdatedBy("erlan");
                    peminjaman.setUpdatedDate(LocalDate.now());
                    peminjamanRepo.save(peminjaman);
                    return GlobalResponse.dataBerhasilDiubah(request);
                }

                if (statusPeminjamanBaru == StatusPeminjamanEnum.DISETUJUI) {
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

                    String requestedStatus = peminjamanDTO.getStatusPengembalian().toUpperCase();
                    StatusPengembalianEnum statusBaru;

// Set untuk status yang valid
                    Set<StatusPengembalianEnum> validStatus = Set.of(
                            StatusPengembalianEnum.DIPINJAM,
                            StatusPengembalianEnum.DIKEMBALIKAN,
                            StatusPengembalianEnum.DIKEMBALIKAN_TERLAMBAT,
                            StatusPengembalianEnum.DIKEMBALIKAN_RUSAK_RINGAN,
                            StatusPengembalianEnum.DIKEMBALIKAN_RUSAK_BERAT,
                            StatusPengembalianEnum.HILANG
                    );

                    // Konversi dan validasi status yang diminta
                    StatusPengembalianEnum requestedEnum = StatusPengembalianEnum.valueOf(requestedStatus);
                    if (!validStatus.contains(requestedEnum)) {
                        return ResponseEntity.badRequest().body("❌ Status pengembalian tidak valid");
                    }
                    // Tambahkan validasi keterlambatan di sini
                    if (requestedEnum == StatusPengembalianEnum.DIKEMBALIKAN && daysLate > 0) {
                        return ResponseEntity.badRequest().body("❌ Status pengembalian 'DIKEMBALIKAN' tidak dapat digunakan jika terlambat. Gunakan status 'DIKEMBALIKAN_TERLAMBAT'.");
                    }
                    // Logika untuk status pengembalian
                    if (daysLate > 0) {
                        // Jika terlambat dan status DIKEMBALIKAN, ubah ke DIKEMBALIKAN_TERLAMBAT
                        if (requestedEnum == StatusPengembalianEnum.DIKEMBALIKAN) {
                            statusBaru = StatusPengembalianEnum.DIKEMBALIKAN_TERLAMBAT;
                        } else {
                            // Untuk status lain (rusak/hilang), tetap gunakan status yang diminta
                            statusBaru = requestedEnum;
                        }
                    } else {
                        // Tidak terlambat, gunakan status yang diminta
                        statusBaru = requestedEnum;
                    }




                    // ✅ Update data peminjaman
                    peminjaman.setStatusPeminjaman(statusPeminjamanBaru);
                    peminjaman.setStatusPengembalian(statusBaru);
                    peminjaman.setTanggalKembali(tanggalKembali);
                    peminjaman.setStatusPembayaran(statusPembayaranPeminjaman);
                    peminjaman.setUpdatedBy(currentUser);
                    peminjaman.setUpdatedDate(LocalDate.now());

                    if (!statusBaru.equals(statusLama)) {  // Hanya proses jika ada perubahan status
                        Set<StatusPengembalianEnum> statusMenambahStok = Set.of(
                                StatusPengembalianEnum.DIKEMBALIKAN,
                                StatusPengembalianEnum.DIKEMBALIKAN_TERLAMBAT,
                                StatusPengembalianEnum.DIKEMBALIKAN_RUSAK_RINGAN
                        );

                        Set<StatusPengembalianEnum> statusMengurangiStok = Set.of(
                                StatusPengembalianEnum.HILANG,
                                StatusPengembalianEnum.DIKEMBALIKAN_RUSAK_BERAT
                        );

                        // **Jika status berubah ke DIPINJAM**
                        if (statusBaru == StatusPengembalianEnum.DIPINJAM) {
                            if (statusLama != StatusPengembalianEnum.DIPINJAM &&
                                    statusLama != StatusPengembalianEnum.HILANG &&
                                    statusLama != StatusPengembalianEnum.DIKEMBALIKAN_RUSAK_BERAT) {
                                if (bukuUpdate.getStok() > 0) {
                                    bukuUpdate.setStok(bukuUpdate.getStok() - 1);
                                }
                            }
                        }
                        // **Jika status berubah ke DIKEMBALIKAN, pastikan stok bertambah**
                        else if (statusMenambahStok.contains(statusBaru)) {
                            if (statusLama == StatusPengembalianEnum.DIPINJAM ||
                                    statusLama == StatusPengembalianEnum.HILANG ||
                                    statusLama == StatusPengembalianEnum.DIKEMBALIKAN_RUSAK_BERAT) {
                                bukuUpdate.setStok(bukuUpdate.getStok() + 1);
                            }
                        }
                        // **Jika status berubah ke HILANG atau RUSAK BERAT, pastikan stok dikurangi dengan benar**
                        else if (statusMengurangiStok.contains(statusBaru)) {
                            if (statusLama == StatusPengembalianEnum.DIPINJAM) {
                                // Tidak perlu menambah/kurangi stok karena buku sudah berkurang saat dipinjam
                            } else if (statusMenambahStok.contains(statusLama)) {
                                // Jika sebelumnya sudah dikembalikan, kurangi stok
                                bukuUpdate.setStok(bukuUpdate.getStok() - 1);
                            }
                        }

                        bukuRepo.save(bukuUpdate);
                    }


                    BigDecimal jumlahDenda;

// Proses denda berdasarkan status pembayaran dan semua jenis status pengembalian
                    if (statusPembayaranPeminjaman == StatusPembayaran.LUNAS) {
                        // Untuk status LUNAS, tetap hitung denda untuk semua status pengembalian
                        jumlahDenda = dendaService.hitungDenda(peminjaman);

                        // Khusus untuk DIKEMBALIKAN, cek keterlambatan
                        if (statusBaru == StatusPengembalianEnum.DIKEMBALIKAN && daysLate > 0) {
                            statusBaru = StatusPengembalianEnum.DIKEMBALIKAN_TERLAMBAT;
                        }
                    } else {
                        // Untuk status belum LUNAS
                        switch (statusBaru) {
                            case DIKEMBALIKAN:
                                if (daysLate > 0) {
                                    statusBaru = StatusPengembalianEnum.DIKEMBALIKAN_TERLAMBAT;
                                    jumlahDenda = dendaService.hitungDenda(peminjaman);
                                } else {
                                    jumlahDenda = BigDecimal.ZERO;
                                    statusPembayaranPeminjaman = StatusPembayaran.LUNAS;
                                }
                                break;

                            case DIKEMBALIKAN_RUSAK_RINGAN:
                            case DIKEMBALIKAN_RUSAK_BERAT:
                            case HILANG:
                                jumlahDenda = dendaService.hitungDenda(peminjaman);
                                break;

                            default:
                                jumlahDenda = dendaService.hitungDenda(peminjaman);
                                break;
                        }
                    }

// Sisanya tetap sama seperti kode sebelumnya
                    if (statusPembayaranPeminjaman == null) {
                        statusPembayaranPeminjaman = jumlahDenda.compareTo(BigDecimal.ZERO) == 0
                                ? StatusPembayaran.LUNAS
                                : StatusPembayaran.BELUM_DIBAYAR;
                    }

                    peminjaman.setStatusPembayaran(statusPembayaranPeminjaman);
                    peminjamanRepo.save(peminjaman);

// Proses penyimpanan denda
                    Optional<Denda> existingDenda = dendaRepo.findByPeminjaman(peminjaman);
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
                    // 🔹 Simpan perubahan peminjaman
                    peminjamanRepo.save(peminjaman);
                    return GlobalResponse.dataBerhasilDiubah(request);
                }
            }
            return GlobalResponse.dataTidakValid("INVALID_STATUS_UPDATE", request);
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
                    peminjaman.getBuku().getJudul(),
                    peminjaman.getBuku().getPenulis(),   // ✅ Sesuai urutan constructor
                    peminjaman.getBuku().getHarga(),     // ✅ Pastikan harga bertipe BigDecimal
                    peminjaman.getBuku().getLinkImage()

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
