package com.example.biliosphere2.service;

import com.example.biliosphere2.model.Buku;
import com.example.biliosphere2.model.Denda;
import com.example.biliosphere2.model.Peminjaman;
import com.example.biliosphere2.model.enums.StatusPengembalianEnum;
import com.example.biliosphere2.model.enums.StatusPembayaran;
import com.example.biliosphere2.repository.BukuRepo;
import com.example.biliosphere2.repository.DendaRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class DendaService {

    private final BukuRepo bukuRepository;
    private final DendaRepo dendaRepo;

    public DendaService(BukuRepo bukuRepository, DendaRepo dendaRepo) {
        this.bukuRepository = bukuRepository;
        this.dendaRepo = dendaRepo;
    }

    // ✅ Metode untuk menghitung denda berdasarkan status pengembalian
    @Transactional
    public BigDecimal hitungDenda(Peminjaman peminjaman) {
        if (peminjaman.getStatusPengembalian() == null) {
            throw new IllegalArgumentException("Status peminjaman tidak boleh kosong!");
        }

        StatusPengembalianEnum status = peminjaman.getStatusPengembalian();
        BigDecimal denda = BigDecimal.ZERO;

        LocalDate batasPengembalian = peminjaman.getTanggalPinjam().plusDays(7);
        LocalDate tanggalKembali = peminjaman.getTanggalKembali();
        LocalDate today = LocalDate.now();

        // Jika buku belum dikembalikan, pakai tanggal hari ini untuk hitung keterlambatan
        if (tanggalKembali == null) {
            tanggalKembali = today;
        }

        // Hitung keterlambatan (hari)
        long daysLate = (tanggalKembali.isAfter(batasPengembalian))
                ? ChronoUnit.DAYS.between(batasPengembalian, tanggalKembali)
                : 0;

        // Jika status DIKEMBALIKAN dan tidak ada keterlambatan, denda tetap Rp 0
        if (status == StatusPengembalianEnum.DIKEMBALIKAN && daysLate == 0) {
            return BigDecimal.ZERO;
        }

        // Konstanta untuk denda
        final BigDecimal DENDA_PER_HARI = BigDecimal.valueOf(5000); // Ubah ke 5000/hari
        final BigDecimal DENDA_RUSAK_RINGAN = BigDecimal.valueOf(30000);

        // Hitung denda berdasarkan status pengembalian
        switch (status) {
            case TERLAMBAT -> {
                if (daysLate > 0) {
                    denda = denda.add(BigDecimal.valueOf(daysLate).multiply(DENDA_PER_HARI));
                }
            }
            case RUSAK_RINGAN -> denda = denda.add(DENDA_RUSAK_RINGAN);
            case RUSAK_BERAT -> {
                Buku buku = getBuku(peminjaman);
                BigDecimal hargaBuku = buku.getHarga();
                denda = denda.add(hargaBuku.multiply(BigDecimal.valueOf(0.50)));
            }
            case RUSAK_BERAT_TERLAMBAT -> {
                Buku buku = getBuku(peminjaman);
                BigDecimal hargaBuku = buku.getHarga();
                denda = denda.add(hargaBuku.multiply(BigDecimal.valueOf(0.50)));
                if (daysLate > 0) {
                    denda = denda.add(BigDecimal.valueOf(daysLate).multiply(DENDA_PER_HARI));
                }
            }
            case HILANG -> {
                Buku buku = getBuku(peminjaman);
                denda = denda.add(buku.getHarga());
            }
            case HILANG_TERLAMBAT -> {
                Buku buku = getBuku(peminjaman);
                denda = denda.add(buku.getHarga());
                if (daysLate > 0) {
                    denda = denda.add(BigDecimal.valueOf(daysLate).multiply(DENDA_PER_HARI));
                }
            }
            case RUSAK_RINGAN_TERLAMBAT -> {
                denda = denda.add(DENDA_RUSAK_RINGAN);
                if (daysLate > 0) {
                    denda = denda.add(BigDecimal.valueOf(daysLate).multiply(DENDA_PER_HARI));
                }
            }
        }

        return denda;
    }
    // ✅ Method helper untuk mendapatkan buku
    private Buku getBuku(Peminjaman peminjaman) {
        return bukuRepository.findById(peminjaman.getBuku().getId())
                .orElseThrow(() -> new IllegalArgumentException("Buku tidak ditemukan!"));
    }
}
