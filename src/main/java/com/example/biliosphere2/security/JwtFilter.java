package com.example.biliosphere2.security;

/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/30/2025 5:48 PM
@Last Modified 2/8/2025 5:48 PM
Version 1.1
*/

import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.service.AppUserDetailService;
import com.example.biliosphere2.service.TokenBlacklistService;
import com.example.biliosphere2.util.LoggingFile;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private AppUserDetailService appUserDetailService;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private TokenBlacklistService tokenBlacklistService; // Tambahkan service blacklist

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        authorization = authorization == null ? "" : authorization;
        String token = null;
        String userName = null;
        try {
            if (!"".equals(authorization) && authorization.startsWith("Bearer ") && authorization.length() > 7) {
                token = authorization.substring(7); // Memotong "Bearer "
                token = Crypto.performDecrypt(token); // Dekripsi token jika dienkripsi

                // **Cek apakah token sudah di-blacklist**
                if (tokenBlacklistService.isTokenBlacklisted(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token sudah tidak valid (logout).");
                    return;
                }

                userName = jwtUtility.getUsernameFromToken(token);
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtility.validateToken(token)) {
                        final UserDetails userDetails = appUserDetailService.loadUserByUsername(userName);
                        final UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception ex) {
            LoggingFile.logException("JwtFilter", "doFilterInternal", ex, OtherConfig.getEnableLogFile());
        }
        filterChain.doFilter(request, response);
    }
}
