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
    private StatusPengembalianRepo statusPengembalianRepo;
    @Autowired
    private DendaService dendaService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TransformPagination transformPagination;

    @Override
    public ResponseEntity<Object> save(ValPeminjamanDTO peminjamanDTO, HttpServletRequest request) {
        try {
            if (peminjamanDTO == null) {
                return GlobalResponse.dataTidakValid("FVAUT03001", request);
            }

            // Cek apakah user dan buku ada di database
            Optional<User> user = userRepo.findById(peminjamanDTO.getIdUser());
            Optional<Buku> buku = bukuRepo.findById(peminjamanDTO.getIdBuku());

            if (user.isEmpty()) {
                return GlobalResponse.dataTidakValid("FVAUT03002", request); // User tidak ditemukan
            }
            if (buku.isEmpty()) {
                return GlobalResponse.dataTidakValid("FVAUT03003", request); // Buku tidak ditemukan
            }

            // Cek apakah user sudah mencapai batas maksimal peminjaman (2 buku)
            long jumlahPeminjamanAktif = peminjamanRepo.countByUserIdAndTanggalKembaliIsNull(peminjamanDTO.getIdUser());
            if (jumlahPeminjamanAktif >= 2) {
                return ResponseEntity.badRequest().body("❌ Peminjaman gagal! Maksimal 2 buku per anggota.");
            }

            // Cek apakah peminjaman sudah ada (duplikasi)
            boolean peminjamanAktif = peminjamanRepo.existsByUser_IdAndBuku_IdAndStatusPengembalian_IdNot(
                    peminjamanDTO.getIdUser(), peminjamanDTO.getIdBuku(), 2L // ID 2 = "Dikembalikan"
            );

            if (peminjamanAktif) {
                return ResponseEntity.badRequest().body("Peminjaman untuk buku ini masih aktif!");
            }

            // Cek apakah stok buku tersedia
            Buku bukuDipinjam = buku.get();
            if (bukuDipinjam.getStok() <= 0) {
                return ResponseEntity.badRequest().body("❌ Buku tidak tersedia");
            }

            // Cek status pengembalian
            Optional<StatusPengembalian> status = statusPengembalianRepo.findById(
                    peminjamanDTO.getIdStatusPengembalian() != null ? peminjamanDTO.getIdStatusPengembalian() : 1L
            );
            if (status.isEmpty()) {
                return GlobalResponse.dataTidakValid("FVAUT03004", request);
            }

            // Simpan peminjaman baru
            Peminjaman peminjaman = new Peminjaman();
            peminjaman.setUser(user.get());
            peminjaman.setBuku(bukuDipinjam);
            peminjaman.setStatusPengembalian(status.get());
            peminjaman.setTanggalPinjam(peminjamanDTO.getTanggalPinjam());
            peminjaman.setTanggalKembali(peminjamanDTO.getTanggalKembali());
            peminjaman.setCreatedBy("erlan");
            peminjaman.setCreatedDate(LocalDate.now());

            // Kurangi stok buku
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
            Long idStatusLama = peminjaman.getStatusPengembalian().getId();

            // ✅ Validasi data
            Optional<User> user = userRepo.findById(peminjamanDTO.getIdUser());
            Optional<Buku> buku = bukuRepo.findById(peminjamanDTO.getIdBuku());
            Optional<StatusPengembalian> status = statusPengembalianRepo.findById(peminjamanDTO.getIdStatusPengembalian());
            if (user.isEmpty() || buku.isEmpty() || status.isEmpty()) {
                return GlobalResponse.dataTidakDitemukan(request);
            }
            Long idStatusBaru = status.get().getId();

            // ✅ Ambil daftar status pengembalian dari database
            List<StatusPengembalian> daftarStatus = statusPengembalianRepo.findAll();
            Map<Long, String> statusMap = daftarStatus.stream()
                    .collect(Collectors.toMap(StatusPengembalian::getId, StatusPengembalian::getStatus));

            // ✅ Validasi ID status pengembalian
            if (!statusMap.containsKey(idStatusBaru)) {
                return ResponseEntity.badRequest().body("ID status pengembalian tidak valid");
            }

            // ✅ Validasi tanggal kembali
            if (peminjamanDTO.getTanggalKembali() != null &&
                    peminjamanDTO.getTanggalKembali().isBefore(peminjaman.getTanggalPinjam())) {
                return ResponseEntity.badRequest()
                        .body("Tanggal kembali tidak boleh sebelum tanggal pinjam");
            }

            // ✅ Tanggal kembali wajib diisi untuk status tertentu
            if ((statusMap.get(idStatusBaru).equals("Dikembalikan") || statusMap.get(idStatusBaru).equals("Terlambat")) &&
                    peminjamanDTO.getTanggalKembali() == null) {
                return ResponseEntity.badRequest()
                        .body("Tanggal kembali wajib diisi untuk status ini");
            }

            // ✅ Update data peminjaman
            peminjaman.setUser(user.get());
            peminjaman.setBuku(buku.get());
            peminjaman.setStatusPengembalian(status.get());
            peminjaman.setTanggalKembali(peminjamanDTO.getTanggalKembali());
            peminjaman.setUpdatedBy(request.getHeader("X-User"));
            peminjaman.setUpdatedDate(LocalDate.now());

            // ✅ Proses perubahan stok buku
            if (!idStatusBaru.equals(idStatusLama)) {
                Buku bukuUpdate = buku.get();
                if (statusMap.get(idStatusBaru).equals("Dikembalikan") || statusMap.get(idStatusBaru).equals("Diganti")) {
                    bukuUpdate.setStok(bukuUpdate.getStok() + 1);
                } else if (statusMap.get(idStatusBaru).equals("Hilang")) {
                    bukuUpdate.setStok(bukuUpdate.getStok() - 1);
                }
                bukuRepo.save(bukuUpdate);
            }

            // ✅ Cek apakah status baru butuh denda
            if (statusMap.get(idStatusBaru).equalsIgnoreCase("Terlambat") ||
                    statusMap.get(idStatusBaru).equalsIgnoreCase("Hilang") ||
                    statusMap.get(idStatusBaru).equalsIgnoreCase("Rusak")) {

                BigDecimal jumlahDenda = dendaService.hitungDenda(peminjaman, statusMap.get(idStatusBaru)); // ✅ FIX

                // Cek apakah denda sudah ada untuk peminjaman ini
                Optional<Denda> existingDenda = dendaRepo.findByPeminjaman(peminjaman);
                if (existingDenda.isPresent()) {
                    Denda denda = existingDenda.get();
                    denda.setJumlahDenda(jumlahDenda);
                    denda.setTanggalDenda(LocalDate.now());
                    denda.setCreatedBy("erlan");
                    dendaRepo.save(denda);
                } else {
                    Denda dendaBaru = new Denda();
                    dendaBaru.setPeminjaman(peminjaman);
                    dendaBaru.setJumlahDenda(jumlahDenda);
                    dendaBaru.setCreatedBy("erlan");
                    dendaBaru.setTanggalDenda(LocalDate.now());
                    dendaBaru.setStatusPembayaran(StatusPembayaran.BELUM_DIBAYAR);

                    System.out.println("Status Pembayaran: " + dendaBaru.getStatusPembayaran());
                    dendaRepo.save(dendaBaru);
                }
            }

            // ✅ Ambil status pembayaran dari Denda
            Optional<Denda> dendaOptional = dendaRepo.findByPeminjaman(peminjaman);
            if (dendaOptional.isPresent()) {
                StatusPembayaran statusPembayaran = dendaOptional.get().getStatusPembayaran();

                // Tambahkan log untuk debugging
                System.out.println("Status Pembayaran dari Denda: " + statusPembayaran);

                // Lakukan sesuatu dengan status pembayaran, misalnya, simpan ke response
            } else {
                System.out.println("Tidak ada denda, anggap status pembayaran LUNAS");
            }

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
        RespPeminjamanDTO respPeminjamanDTO = modelMapper.map(peminjaman, RespPeminjamanDTO.class);

        // Set status pengembalian (cek apakah tidak null)
        if (peminjaman.getStatusPengembalian() != null) {
            respPeminjamanDTO.setStatusPengembalian(
                    new RespStatusPengembalianDTO(
                            peminjaman.getStatusPengembalian().getId(),
                            peminjaman.getStatusPengembalian().getStatus()
                    )
            );
        }

        // Set data buku (pastikan konstruktor RespBukuDTO udah ada)
        if (peminjaman.getBuku() != null) {
            respPeminjamanDTO.setBuku(
                    new RespBukuDTO(peminjaman.getBuku().getId(), peminjaman.getBuku().getJudul())
            );
        }

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