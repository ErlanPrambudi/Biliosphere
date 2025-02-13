package com.example.biliosphere2.service;

import com.example.biliosphere2.model.Peminjaman;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class DendaService {
    @Transactional
    public BigDecimal hitungDenda(Peminjaman peminjaman, String status) {
        BigDecimal denda = BigDecimal.ZERO;
        LocalDate dueDate = peminjaman.getTanggalKembali();
        LocalDate returnDate = LocalDate.now();

        switch (status.toUpperCase()) {
            case "TERLAMBAT":
                long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
                if (daysLate > 0) {
                    denda = BigDecimal.valueOf(daysLate).multiply(BigDecimal.valueOf(5000));
                }
                break;
            case "HILANG":
                denda = BigDecimal.valueOf(100000);
                break;
            case "RUSAK":
                denda = BigDecimal.valueOf(50000);
                break;
            case "DIGANTI":
            case "DIKEMBALIKAN":
                denda = BigDecimal.ZERO;
                break;
        }
        return denda;
    }
}
