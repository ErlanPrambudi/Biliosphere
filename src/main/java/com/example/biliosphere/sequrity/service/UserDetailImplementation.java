package com.example.biliosphere.sequrity.service;

import com.example.biliosphere.model.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailImplementation implements UserDetails {

    private String username; // Menggunakan nama pengguna sebagai username
    private String email;    // Email sebagai identitas otentikasi utama
    private String name;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> roles;

    // Constructor
    public UserDetailImplementation(String username, String email, String name, String password,
                                    Collection<? extends GrantedAuthority> roles) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    // Factory method
    public static UserDetailImplementation build(Users users) {
        List<GrantedAuthority> authorities = users.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new UserDetailImplementation(
                users.getNama(),  // Gunakan nama sebagai username
                users.getEmail(),
                users.getNama(),
                users.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username; // Nama pengguna (nama)
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
