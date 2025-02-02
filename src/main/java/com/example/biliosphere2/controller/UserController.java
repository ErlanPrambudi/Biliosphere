package com.example.biliosphere2.controller;

import com.example.biliosphere2.dto.validasi.ValUserDTO;
import com.example.biliosphere2.model.User;
import com.example.biliosphere2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Transactional
    @PostMapping("")
    public ResponseEntity<Object> save(@RequestBody ValUserDTO userDTO,
                                       HttpServletRequest request) {
        User user = userService.convertToUser(userDTO);
        return userService.save(user, request);
    }
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id,
                                         @RequestBody ValUserDTO userDTO,
                                         HttpServletRequest request) {
        User user = userService.convertToUser(userDTO);
        return userService.update(id, user, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id,
                                         HttpServletRequest request) {
        return userService.delete(id, request);
    }

    @GetMapping("")
    public ResponseEntity<Object> findAll(Pageable pageable,
                                          HttpServletRequest request) {
        return userService.findAll(pageable, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable("id") Long id,
                                           HttpServletRequest request) {
        return userService.findById(id, request);
    }

    @GetMapping("/search/{columnName}/{value}")
    public ResponseEntity<Object> findByParam(Pageable pageable,
                                              @PathVariable("columnName") String columnName,
                                              @PathVariable("value") String value,
                                              HttpServletRequest request) {
        return userService.findByParam(pageable, columnName, value, request);
    }
}