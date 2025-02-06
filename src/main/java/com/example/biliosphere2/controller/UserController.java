package com.example.biliosphere2.controller;

import com.example.biliosphere2.dto.validasi.ValUserDTO;
import com.example.biliosphere2.model.User;
import com.example.biliosphere2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> save(@RequestBody ValUserDTO userDTO,
                                       HttpServletRequest request) {
        User user = userService.convertToUser(userDTO);
        return userService.save(user, request);
    }
    @Transactional
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> update(@PathVariable("id") Long id,
                                         @RequestBody ValUserDTO userDTO,
                                         HttpServletRequest request) {
        User user = userService.convertToUser(userDTO);
        return userService.update(id, user, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id,
                                         HttpServletRequest request) {
        return userService.delete(id, request);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> findAll(Pageable pageable,
                                          HttpServletRequest request) {
        return userService.findAll(pageable, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id,
                                           HttpServletRequest request) {
        return userService.findById(id, request);
    }

    @GetMapping("/search/{columnName}/{value}")
    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<Object> findByParam(Pageable pageable,
                                              @PathVariable("columnName") String columnName,
                                              @PathVariable("value") String value,
                                              HttpServletRequest request) {
        return userService.findByParam(pageable, columnName, value, request);
    }
}