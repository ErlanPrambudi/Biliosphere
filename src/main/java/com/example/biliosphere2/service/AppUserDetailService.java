package com.example.biliosphere2.service;
/*
IntelliJ IDEA 2024.3 (Ultimate Edition)
Build #IU-243.21565.193, built on November 13, 2024
@Author Dell Erlan Prambudi
Java Developer
Created on 1/28/2025 7:49 PM
@Last Modified 1/28/2025 7:49 PM
Version 1.0
*/


import com.example.biliosphere2.config.OtherConfig;
import com.example.biliosphere2.dto.response.MenuLoginDTO;
import com.example.biliosphere2.dto.validasi.ValRegisDTO;
import com.example.biliosphere2.dto.validasi.ValLoginDTO;
import com.example.biliosphere2.dto.validasi.ValVerifyRegisDTO;
import com.example.biliosphere2.handler.ResponseHandler;
import com.example.biliosphere2.model.Akses;
import com.example.biliosphere2.model.User;
import com.example.biliosphere2.repository.UserRepo;
import com.example.biliosphere2.security.BcryptImpl;
import com.example.biliosphere2.security.Crypto;
import com.example.biliosphere2.security.JwtUtility;
import com.example.biliosphere2.util.TransformationData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper ;
    //    private ModelMapper modelMapper = new ModelMapper();
    private Random random = new Random();

    @Autowired
    private JwtUtility jwtUtility;

    public ResponseEntity<Object> login(User user, HttpServletRequest request) throws UsernameNotFoundException {
        Optional<User> optUser = userRepo.findByUsername(user.getUsername());
        if (!optUser.isPresent()) {
            return new ResponseHandler().handleResponse("USER TIDAK TERDAFTAR",
                    HttpStatus.BAD_REQUEST,
                    null,"X01004",request);
        }
        User userDB = optUser.get();
        if(!userDB.getRegistered()){
            return new ResponseHandler().handleResponse("USER TIDAK TERDAFTAR",
                    HttpStatus.BAD_REQUEST,
                    null,"X01005",request);
        }
        String password = userDB.getPassword();// yang sudah di hash dari table
        if(!BcryptImpl.verifyHash((user.getUsername()+user.getPassword()), password)){
            return new ResponseHandler().handleResponse("USER NAME ATAU PASSWORD SALAH",
                    HttpStatus.BAD_REQUEST,
                    null,"X01006",request);
        }

        UserDetails userDetails = loadUserByUsername(user.getUsername());
        Map<String,Object> mapForJwt = new HashMap<>();
//        mapForJwt.put("un", userDB.getUsername());
//        mapForJwt.put("pwd", userDB.getPassword());
        mapForJwt.put("uid",userDB.getId());
        mapForJwt.put("ml",userDB.getEmail());
        mapForJwt.put("nl",userDB.getNama());
        mapForJwt.put("pn",userDB.getNoHp());

        String token = jwtUtility.generateToken(userDetails,mapForJwt);
        Map<String,Object> mapResponse = new HashMap<>();
        mapResponse.put("token", Crypto.performEncrypt(token));//kalau mau di encrypt
        List<MenuLoginDTO> ltMenu =  modelMapper.map(userDB.getAkses().getMenuList(),new TypeToken<List<MenuLoginDTO>>(){}.getType());
//        mapResponse.put("token",token);//kalau mau di encrypt
        mapResponse.put("menu",new TransformationData().doTransformAksesMenuLogin(ltMenu));
        return ResponseEntity.status(HttpStatus.OK).body(mapResponse);
    }

    @Transactional
    public ResponseEntity<Object> regis(User user, HttpServletRequest request) throws UsernameNotFoundException {

        /** default untuk proses registrasi, user akan mendapatkan akses dengan ID 2 */
        Akses akses = new Akses();
        akses.setId(2L);

        Optional<User> optUser = userRepo.findByUsername(user.getUsername());
        User userDB = null;
        Integer otp = 0;

        if (optUser.isPresent()) {
            userDB = optUser.get();
            if(userDB.getRegistered()){
                return new ResponseHandler().handleResponse("USER SUDAH TERDAFTAR",
                        HttpStatus.BAD_REQUEST,
                        null,"X01007",request);
            }else {
                List<User> ltUser = userRepo.findByUsernameOrNoHpOrEmailAndIsRegistered(user.getUsername(),user.getNoHp(),user.getEmail(),true);
                if(!ltUser.isEmpty()){
                    return new ResponseHandler().handleResponse("DATA USERNAME/EMAIL/NO-HP SUDAH TERPAKAI",
                            HttpStatus.BAD_REQUEST,
                            null,"X01008",request);
                }else{
                    /** PERNAH REGISTRASI TAPI BELUM SELESAI */
                    userDB.setAlamat(user.getAlamat());
                    userDB.setNoHp(user.getNoHp());
                    userDB.setEmail(user.getEmail());
                    userDB.setNama(user.getNama());
                    userDB.setNoHp(user.getNoHp());
                    userDB.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
                    userDB.setTanggalLahir(user.getTanggalLahir());
                    userDB.setUpdatedBy(userDB.getNama());
                    userDB.setUpdatedDate(new Date());
                    otp = random.nextInt(111111,999999);
                    userDB.setOtp(BcryptImpl.hash(String.valueOf(otp)));
                    userDB.setAkses(akses);
                }
            }
        }else {
            user.setCreatedBy("Paul");
            user.setCreatedDate(new Date());
            user.setPassword(BcryptImpl.hash(user.getUsername()+user.getPassword()));
            otp = random.nextInt(111111,999999);
            user.setOtp(BcryptImpl.hash(String.valueOf(otp)));
            user.setAkses(akses);
            userRepo.save(user);
        }
        /** kirim verifikasi email */
        Map<String,Object> mapResponse = new HashMap<>();
        if(OtherConfig.getEnableAutomation().equals("y")){
            mapResponse.put("token", otp);
        }
        /** kalau mau send email, lihat di class ContohController API kirim-email  */
//        mapResponse.put("estafet",);//untuk security estafet work flow pada form
        return ResponseEntity.status(HttpStatus.OK).body(mapResponse);
    }

    @Transactional
    public ResponseEntity<Object> verifyRegis(User user, HttpServletRequest request) throws UsernameNotFoundException {
        if(user==null){
            return new ResponseHandler().handleResponse("DATA TIDAK VALID",
                    HttpStatus.BAD_REQUEST,
                    null,"X01009",request);
        }
        Optional<User> optionalUser = userRepo.findByEmail(user.getEmail());
        if (!optionalUser.isPresent()) {
            return new ResponseHandler().handleResponse("DATA TIDAK VALID",
                    HttpStatus.BAD_REQUEST,
                    null,"X01008",request);
        }
        User userDB = optionalUser.get();
        if(!BcryptImpl.verifyHash(user.getOtp(),userDB.getOtp())){
            return new ResponseHandler().handleResponse("TOKEN SALAH",
                    HttpStatus.BAD_REQUEST,
                    null,"X01009",request);
        }

        userDB.setOtp(BcryptImpl.hash(String.valueOf(random.nextInt(111111,999999))));
        userDB.setRegistered(true);
        return ResponseEntity.status(HttpStatus.OK).body("Registrasi Berhasil !!");
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User>  opUser = userRepo.findByUsername(username);
        if(!opUser.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        User user = opUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),user.getAuthorities());
    }

    public User convertToUser(ValLoginDTO valLoginDTO) {
        return modelMapper.map(valLoginDTO,User.class);
    }

    public User convertToUser(ValRegisDTO valRegisDTO) {
        return modelMapper.map(valRegisDTO,User.class);
    }

    public User convertToUser(ValVerifyRegisDTO valVerifyRegisDTO) {
        return modelMapper.map(valVerifyRegisDTO,User.class);
    }
}