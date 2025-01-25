package com.example.biliosphere.controller;

import com.example.biliosphere.model.Users;
import com.example.biliosphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    // GET: Ambil semua user
    @GetMapping("/users")
    public List<Users> findAll() {
        return userService.findAll();
    }

    // GET: Ambil user berdasarkan ID
    @GetMapping("/users/{id}")
    public ResponseEntity<Users> findById(@PathVariable("id") String id) {
        Users user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    // POST: Simpan user baru
    @PostMapping("/users")
    public ResponseEntity<Users> save(@RequestBody Users user) {
        // Validasi jika ID dikirimkan dalam request body
        if (user.getId() != null && !user.getId().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Pastikan data users tidak null
        if (user.getNama() == null || user.getNama().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Hapus ID jika ada pada request body sebelum diproses lebih lanjut
        user.setId(null);  // Pastikan ID tidak ada sebelum disimpan.

        // Simpan user menggunakan service
        Users savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // PUT: Update user berdasarkan ID
    @PutMapping("/users/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody Users user) {
        try {
            userService.update(id, user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Jika user tidak ditemukan
        }
    }

    // DELETE: Hapus user berdasarkan ID
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build(); // Jika user tidak ditemukan
        }
    }
}
