package com.example.biliosphere.service;

import com.example.biliosphere.exception.BadRequestException;
import com.example.biliosphere.exception.ResourceNotFoundException;
import com.example.biliosphere.model.Roles;
import com.example.biliosphere.model.Users;
import com.example.biliosphere.repo.RoleRepository;
import com.example.biliosphere.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Menampilkan semua user
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    // Menampilkan user berdasarkan ID
    public Users findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));
    }

    // Menyimpan data user baru
    public Users save(Users user) {
        if (user.getId() != null) {
            throw new BadRequestException("ID should not be provided for a new user");
        }

        // Validasi field
        if (!StringUtils.hasText(user.getNama())) {
            throw new BadRequestException("User name is required");
        }

        if (!StringUtils.hasText(user.getEmail())) {
            throw new BadRequestException("User email is required");
        }

        // Memastikan email belum terdaftar
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email " + user.getEmail() + " is already registered");
        }

        // Mengatur metadata
        user.setCreatedBy("erlan");
        user.setTanggalDaftar(LocalDate.now());

        return userRepository.save(user);
    }

    // Update data user berdasarkan ID
    @Transactional
    public void update(String id, Users user) {
        Users existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + id + " not found"));

        // Update field
        existingUser.setNama(user.getNama());
        existingUser.setAlamat(user.getAlamat());
        existingUser.setNoTelpon(user.getNoTelpon());
        existingUser.setEmail(user.getEmail());
        existingUser.setRoles(user.getRoles());
        existingUser.setUpdateBy("erlan");
        existingUser.setUpdatedDate(LocalDate.now());

        userRepository.save(existingUser);
    }

    // Menghapus data user berdasarkan ID
    @Transactional
    public void delete(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    // Menyimpan user dengan roles yang diberikan dari DTO
    public Users createUserWithRoles(Users user, Set<String> roleIds) {
        // Validasi role
        Set<Roles> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Roles role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + roleId + " not found"));
            roles.add(role);
        }

        user.setRoles(roles);

        return save(user); // Panggil metode save untuk menyimpan user
    }
}
